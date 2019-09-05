package xyz.yuzh.learning.springcloud.zuul.zuulserver.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author Harry Zhang
 * @since 2019-09-05 16:19
 */
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
