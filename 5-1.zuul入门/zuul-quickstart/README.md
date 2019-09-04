<!-- TOC -->

- [Zuul 入门](#zuul-%E5%85%A5%E9%97%A8)
    - [client-a 提供服务接口](#client-a-%E6%8F%90%E4%BE%9B%E6%9C%8D%E5%8A%A1%E6%8E%A5%E5%8F%A3)
    - [zuul-server 搭建](#zuul-server-%E6%90%AD%E5%BB%BA)
    - [测试 zuul 路由转发](#%E6%B5%8B%E8%AF%95-zuul-%E8%B7%AF%E7%94%B1%E8%BD%AC%E5%8F%91)
- [Zuul 配置](#zuul-%E9%85%8D%E7%BD%AE)
    - [路由配置](#%E8%B7%AF%E7%94%B1%E9%85%8D%E7%BD%AE)
        - [单实例 serviceId 映射（路由到服务）](#%E5%8D%95%E5%AE%9E%E4%BE%8B-serviceid-%E6%98%A0%E5%B0%84%E8%B7%AF%E7%94%B1%E5%88%B0%E6%9C%8D%E5%8A%A1)
        - [单实例 url 映射（路由到物理地址）](#%E5%8D%95%E5%AE%9E%E4%BE%8B-url-%E6%98%A0%E5%B0%84%E8%B7%AF%E7%94%B1%E5%88%B0%E7%89%A9%E7%90%86%E5%9C%B0%E5%9D%80)
        - [多实例路由（使用 Eureka）](#%E5%A4%9A%E5%AE%9E%E4%BE%8B%E8%B7%AF%E7%94%B1%E4%BD%BF%E7%94%A8-eureka)
        - [多实例路由（使用 Ribbon）](#%E5%A4%9A%E5%AE%9E%E4%BE%8B%E8%B7%AF%E7%94%B1%E4%BD%BF%E7%94%A8-ribbon)
        - [forward 本地跳转](#forward-%E6%9C%AC%E5%9C%B0%E8%B7%B3%E8%BD%AC)
        - [相同路径的加载规则](#%E7%9B%B8%E5%90%8C%E8%B7%AF%E5%BE%84%E7%9A%84%E5%8A%A0%E8%BD%BD%E8%A7%84%E5%88%99)
        - [路径通配符](#%E8%B7%AF%E5%BE%84%E9%80%9A%E9%85%8D%E7%AC%A6)
    - [功能配置](#%E5%8A%9F%E8%83%BD%E9%85%8D%E7%BD%AE)
        - [路由前缀](#%E8%B7%AF%E7%94%B1%E5%89%8D%E7%BC%80)
        - [服务屏蔽和路径屏蔽](#%E6%9C%8D%E5%8A%A1%E5%B1%8F%E8%94%BD%E5%92%8C%E8%B7%AF%E5%BE%84%E5%B1%8F%E8%94%BD)
        - [切断敏感头信息](#%E5%88%87%E6%96%AD%E6%95%8F%E6%84%9F%E5%A4%B4%E4%BF%A1%E6%81%AF)
        - [重试机制](#%E9%87%8D%E8%AF%95%E6%9C%BA%E5%88%B6)

<!-- /TOC -->

# Zuul 入门

项目结构：

    └── zuul-quickstart     项目根目录
        ├── client-a        7070 下游服务（实际被调用的服务）
        ├── eureka-server   8761 注册中心
        └── zuul-server     5555 路由网关

### client-a 提供服务接口

```java
@RestController
public class TestController {

    @GetMapping("/add")
    public Integer add(Integer a, Integer b) {
        return a + b;
    }

}
```


### zuul-server 搭建

1. 依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
</dependencies>
```

2. application.yml

```yml
spring:
  application:
    name: zuul-server

server:
  port: 5555

eureka:
  client:
    service-url:
      default-zone: http:/localhost:8761/eureka/
  instance:
    prefer-ip-address: true

zuul:
  routes:
    client-a:
      # 所有以 client 开头的请求映射到 client-a 这个服务中去
      path: /client/**
      serviceId: client-a
```

3. 主配置类

```java
@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
public class ZuulServerApplication {}
```

### 测试 zuul 路由转发

1. 调用服务实际 url：http://localhost:7070/add?a=1&b=2
2. 通过 zuul 路由调用：http://localhost:5555/client/add?a=1&b=2
 ![](http://img.yuzh.xyz/20190904142541_plQkB5_Screenshot.png)

# Zuul 配置
## 路由配置
### 单实例 serviceId 映射（路由到服务）
一个 url 到服务的映射完整规则如下：

```yml
zuul:
  routes:
    client-a:
      path: /client/**
      serviceId: client-a
```

以上配置可以简化为：

```yml
zuul:
    routes:
        client-a: /client/**
```

和：

```yml
zuul:
    routes:
        client-a:
```

### 单实例 url 映射（路由到物理地址）
除了路由到服务外，还可以路由到物理地址，将 serviceId 替换为 url 即可：

```yml
zuul:
  routes:
    client-a:
      path: /client/**
      # 路由到服务
      # serviceId: client-a
      # 路由到地址
      url: http://localhost:7070
```

### 多实例路由（使用 Eureka）
默认情况下，zuul 会使用 Eureka 中集成的基本负载均衡能力。

1. 拷贝 client-a 项目，重命名为 client-b，除了端口（7071）之外其他保持一致。服务名都是：`client-a`
![](http://img.yuzh.xyz/20190904144703_1P4EY5_Screenshot.png)

2. 修改 client-b 接口：
    ```java
    @GetMapping("/add")
    public String add(Integer a, Integer b) {
        return "client b: " + (a + b);
    }
    ```

3. 启动 eureka-server、client-a、client-b、zuul-server 后调用：http://localhost:5555/client/add?a=1&b=2
![](http://img.yuzh.xyz/20190904145234_xKY0pg_Screenshot.png)

### 多实例路由（使用 Ribbon）

1. 添加 ribbon 依赖

```xml
<dependencies>
   ......
   <!--    使用 Ribbon 负载均衡默认的 Eureka 负载均衡（可选）    -->
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
   </dependency>
</dependencies>
```

2. 添加 application-ribbon.xml

```yml
# --------------------------------------------------------------  使用 Ribbon 负载均衡替代默认 Eureka 负载均衡的配置
spring:
  application:
    name: zuul-server

server:
  port: 5555

# eureka 配置可以不用了
#eureka:
#  client:
#    service-url:
#      default-zone: http:/localhost:8761/eureka/
#  instance:
#    prefer-ip-address: true

zuul:
  routes:
    ribbon-route:
      # 访问路径
      path: /ribbon/**
      # 引用下面的 ribbon-route-test 配置
      serviceId: ribbon-route-test

ribbon:
  eureka:
    # 禁止 Ribbon 使用 Eureka
    enabled: false

# zuul 的 ribbon 负载均衡配置
ribbon-route-test:
  ribbon:
    NIWSServerListClassName: com.netflix.loadbalancer.ConfigurationBasedServerList
    # Ribbon LB Strategy
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
    # client services for Ribbon LB
    listOfServers: localhost:7070, localhost:7071
```

3. 指定 application-ribbon.xml 启动：`java -jar zuul-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=ribbon`
4. 验证
![](http://img.yuzh.xyz/20190904153555_IiEeWa_Screenshot.png)

### forward 本地跳转
有时会在 zuul 中做一些逻辑处理，在网关中写一个接口。希望访问某个路径时跳到这个接口中去。

1. 网关接口

```java
@RestController
public class TestController {

    @GetMapping("/client")
    public String add(Integer a, Integer b) {
        return "zuul 接口：" + (a + b);
    }

}
```

2. application-ribbon.yml

```yml
# --------------------------------------------------------------  测试 forward 转发到本地接口
spring:
  application:
    name: zuul-server

server:
  port: 5555

eureka:
  client:
    service-url:
      default-zone: http:/localhost:8761/eureka/
  instance:
    prefer-ip-address: true

zuul:
  routes:
    # 原来的 client-a 服务还是能调用
    client-a:
      path: /client/**
      serviceId: client-a
    # 访问 http://localhost:5555/zuul-forward?a=1&b=6 时跳到 /client 接口
    test-zuul-forward:
      path: /zuul-forward/**
      url: forward:/client
```

3. 演示
![](http://img.yuzh.xyz/20190904155433_UXNrJK_Screenshot.png)

### 相同路径的加载规则

以下配置中，路径 `/client/**` 匹配了多个服务，此时之后最后一个会生效。即：client-b 能够被调用，client-a 不行。

```yml
zuul:
    routes:
        client-a:
            path: /client/**
            serviceId: client-a
        client-a:
            path: /client/**
            serviceId: client-b
```

### 路径通配符

规则 | 解释                     | 示例
-----|--------------------------|-------------------------------------------------------------------
`/**`  | 匹配任意数量的路径和字符 | /client/add, /client/mul, /client/a, /client/aa/a, /client/mul/a/b
`/*`   | 匹配任意数量的字符       | /client/add, /client/mul, /client/c
`/?`   | 匹配单个字符             | /client/a, /client/b

## 功能配置
### 路由前缀

添加以下配置之后访问方式为：http://localhost:5555/pre/client/add?a=1&b=2

```yml
zuul:
  # 指定前缀，访问必须带上前缀 /pre/client/**
  prefix: /pre
  routes:
    client-a:
      path: /client/**
      serviceId: client-a
```

http://localhost:5555/client/add?a=1&b=2 将会访问失败。

### 服务屏蔽和路径屏蔽

1. application-ignored.yml

```yml
zuul:
  # 服务 client-b 将被屏蔽掉
  ignored-services: client-b
  # 接口中带有 test 的将会被屏蔽掉
  ignored-patterns: /**/test/**
  prefix: /pre

  routes:
    client-a:
      path: /client/**
      serviceId: client-a
```

2. 添加接口

```java
/**
 * 测试：zuul 中如果配置了 ignored-patterns: /test/**  此接口将不能通过 zuul 调通
 */
@GetMapping("/client/test/aaaa")
public String test(Integer a, Integer b) {
    return "/client/test/aaaa 接口：" + (a + b);
}
```

3. 测试 http://localhost:5555/pre/client/add/test/aaaa?a=1&b=2

        Whitelabel Error Page
        This application has no explicit mapping for /error, so you are seeing this as a fallback.

        Wed Sep 04 16:36:38 CST 2019
        There was an unexpected error (type=Not Found, status=404).
        No message available

### 切断敏感头信息
当我们自己的系统通过 zuul 转发到其他外部系统时，为了安全考虑。可以通过配置切断请求头的传递。

1. 添加 client-a 服务的接口

```java
/**
 * 测试：zuul 的敏感头传递
 */
@GetMapping("/header")
public Map<String, String> header(HttpServletRequest request) {
    Map<String, String> map = new HashMap<>();
    map.put("Cookie", request.getHeader("Cookie"));
    map.put("Set-Cookie", request.getHeader("Set-Cookie"));
    map.put("Authorization", request.getHeader("Authorization"));
    map.put("Can-Read", request.getHeader("Can-Read"));
    return map;
}
```

2. application-removeheader.yml

```yml
zuul:
  prefix: /pre
  routes:
    client-a:
      path: /client/**
      serviceId: client-a
      # 这些头信息不会传递（Cookie, Set-Cookie, Authorization 这三个默认是添加的，不指定也可以）
      sensitiveHeaders: Cookie, Set-Cookie, Authorization
```

3. 测试：使用默认 profile 访问，由于 Cookie, Set-Cookie, Authorization 默认添加，所以先设置 sensitiveHeaders 为空，测试调用结果：
![](http://img.yuzh.xyz/20190904170758_b14hZL_Screenshot.png)

4. 测试：使用 profile=removeheader 访问，测试调用结果：
![](http://img.yuzh.xyz/20190904171112_FTFfGw_Screenshot.png)

### 重试机制

zuul 配合 ribbon 可实现重试，但该功能要谨慎使用，如某些接口要保证幂等性。

```yml
zuul:
    retryable: true #开启重试

ribbon:
    MaxAutoRetries: 1 #同一个服务重试的次数(除去首次)
    MaxAutoRetriesNextServer: 1  #切换相同服务数量
```
