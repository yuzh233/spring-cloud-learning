package xyz.yuzh.learning.springcloud.inaction.zuulserver.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

/**
 * @author Harry Zhang
 * @since 2019-09-10 22:17
 */
@Component
public class ServiceCallFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        context.addZuulRequestHeader("x-service-call", "111");
        return null;
    }
}
