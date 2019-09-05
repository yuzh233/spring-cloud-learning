package xyz.yuzh.learning.springcloud.zuul.zuulserver.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

/**
 * @author Harry Zhang
 * @since 2019-09-05 16:20
 */
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
