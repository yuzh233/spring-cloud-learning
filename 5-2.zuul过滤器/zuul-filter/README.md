
# Zuul 工作原理

# Zuul 原生 Filter
查看原生 filter 及其详情需要搭配 `spring boot actuator + @EnableZuulProxy`  支持，spring-cloud-starter-netflix-zuul 默认依赖了 spring-boot-starter-actuator，所以只需要修改配置文件开放管控端点：

1）首先拷贝 zuul-quick-start 的项目作为一个新项目 zuul-filter：

    └── zuul-filter
        ├── client-a
        ├── eureka-server
        └── zuul-server

2）application.yml

```yml
# actuator 启用所有的监控端点. “*” 号代表启用所有的监控端点，可以单独启用，例如，health，info，metrics
# spring boot 升为 2.0 后，为了安全，默认 Actuator 只暴露了2个端点，heath 和 info
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: ALWAYS
```

3）访问 http://localhost:5555/actuator/routes 查看 zuul 中已有的映射规则

```json
{
    "/client/**": "client-a"
}
```

4）访问 http://localhost:5555/actuator/routes/details 可以查看明细

```json
{
    "/client/**": {
        "id": "client-a",
        "fullPath": "/client/**",
        "location": "client-a",
        "path": "/**",
        "prefix": "/client",
        "retryable": false,
        "customSensitiveHeaders": false,
        "prefixStripped": true
    }
}
```

5）访问 http://localhost:5555/actuator/filters 查看 zuul server 中已经生效的 filter

```json
{
    "error": [
        {
            "class": "org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter",
            "order": 0,
            "disabled": false,
            "static": true
        }
    ],
    "post": [
        {
            "class": "org.springframework.cloud.netflix.zuul.filters.post.SendResponseFilter",
            "order": 1000,
            "disabled": false,
            "static": true
        }
    ],
    "pre": [
        {
            "class": "org.springframework.cloud.netflix.zuul.filters.pre.DebugFilter",
            "order": 1,
            "disabled": false,
            "static": true
        },
        {
            "class": "org.springframework.cloud.netflix.zuul.filters.pre.FormBodyWrapperFilter",
            "order": -1,
            "disabled": false,
            "static": true
        },
        {
            "class": "org.springframework.cloud.netflix.zuul.filters.pre.Servlet30WrapperFilter",
            "order": -2,
            "disabled": false,
            "static": true
        },
        {
            "class": "org.springframework.cloud.netflix.zuul.filters.pre.ServletDetectionFilter",
            "order": -3,
            "disabled": false,
            "static": true
        },
        {
            "class": "org.springframework.cloud.netflix.zuul.filters.pre.PreDecorationFilter",
            "order": 5,
            "disabled": false,
            "static": true
        }
    ],
    "route": [
        {
            "class": "org.springframework.cloud.netflix.zuul.filters.route.SimpleHostRoutingFilter",
            "order": 100,
            "disabled": false,
            "static": true
        },
        {
            "class": "org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter",
            "order": 10,
            "disabled": false,
            "static": true
        },
        {
            "class": "org.springframework.cloud.netflix.zuul.filters.route.SendForwardFilter",
            "order": 500,
            "disabled": false,
            "static": true
        }
    ]
}
```

通过 actuator 监控显示了原有的所有 filter，包括其`全类名、执行顺序、是否禁用、是否为静态方法`。对于我们定制自己的 filter 时有很大帮助。

如果想要禁用某个 filter，如 `SendErrorFilter`：

```properties
zuul.SendErrorFilter.error.disabled=true
```

# 第一个自定义 Zuul Filter
自定义 filter，只需要继承 ZuulFilter，实现四个方法即可：

```java
@Component
public class FirstPreFilter extends ZuulFilter {

    /**
     * 指定 filter 类型
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 指定 filter 顺序
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 指定 filter 是否生效
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 指定 filter 处理逻辑
     */
    @Override
    public Object run() {
        System.out.println("这是第一个自定义Zuul Filter！");
        return null;
    }

}
```

接下来验证此过滤器是否生效，访问 http://localhost:5555/actuator/filters 查看是否在过滤器链中：

![](http://img.yuzh.xyz/20190905161418_vg0taF_Screenshot.png)

访问 http://localhost:5555/client/add?a=1&b=2 查看控制台是否打印：

![](http://img.yuzh.xyz/20190905161521_8h5LK8_Screenshot.png)

# Zuul Filter 业务处理实战

需求：使用 SecondFilter 来验证是否传入了 a 参数，使用 ThridFilter 来验证是否传入了 b 参数，最后在 PostFilter 里统一处理返回内容。流程图如下：

![](http://img.yuzh.xyz/20190905161834_IFuTIM_Screenshot.png)

SecondPreFilter：

```java
@Component
public class SecondPreFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        // 此过滤器永远生效
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("进入了 SecondPreFilter！");
        // RequestContext 是 zuul 的过滤器链之间传递数据的静态对象，内部使用的是 ThreadLocal
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String a = request.getParameter("a");
        if (null == a) {
            System.out.println("\tSecondPreFilter 业务异常！");
            // 对该请求禁止路由，也就是禁止访问下游服务
            context.setSendZuulResponse(false);
            // 设置响应信息供 PostFilter 返回
            context.setResponseBody("{'status':500, 'message':'a 参数为空！'}");
            // 自定义变量，用于标示当前过滤请求是否通过。同类型的下一个 filter 根据此变量决定是否生效。
            context.set("logicIsSuccess", false);
            return null;
        }
        // 避免报空
        context.set("logicIsSuccess", true);
        return null;
    }
}
```

ThirdPreFilter:

```java
@Component
public class ThirdPreFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        // 如果 SecondPreFilter 过滤器没有通过这个过滤器就没必要启动了
        return (boolean) RequestContext.getCurrentContext().get("logicIsSuccess");
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("进入了 ThirdPreFilter！");
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String b = request.getParameter("b");
        if (null == b) {
            System.out.println("\tThirdPreFilter 业务异常！");
            context.setSendZuulResponse(false);
            context.setResponseBody("{'status':500, 'message':'b 参数为空！'}");
            context.set("logicIsSuccess", false);
            return null;
        }
        context.set("logicIsSuccess", true);
        return null;
    }
}
```

PostFilter:

```java
@Component
public class PostFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 后置过滤器，在当前业务中必须执行的，故指定为 true
        return true;
    }

    @Override
    public Object run() {
        System.out.println("进入了 PostFilter！");
        RequestContext context = RequestContext.getCurrentContext();
        context.getResponse().setCharacterEncoding("UTF-8");
        // 上游 Filter 设置的响应内容
        String responseBody = context.getResponseBody();
        if (null != responseBody) {
            // 如果存在内容说明处理失败了
            context.setResponseStatusCode(500);
            context.setResponseBody(responseBody);
        }
        return null;
    }
}
```

1. 首先给全参数正常访问 http://localhost:5555/client/add?a=2&b=1
![结果](http://img.yuzh.xyz/20190905165559_UZ4Y7T_Screenshot.png)
![控制台打印](http://img.yuzh.xyz/20190905164146_ugYPly_Screenshot.png)

2. 只传递参数 b，预测：进入 SecondFilter，抛出业务异常，不进入 ThirdFilter
![](http://img.yuzh.xyz/20190905165752_Ov2ppJ_Screenshot.png)
![](http://img.yuzh.xyz/20190905165817_i53J8B_Screenshot.png)

3. 只传递参数 a，预测：进入 SecondFilter，进入 ThirdFilter，PostFilter 抛出业务异常
![](http://img.yuzh.xyz/20190905165923_pMFOsO_Screenshot.png)
![](http://img.yuzh.xyz/20190905165953_3zEOwI_Screenshot.png)
