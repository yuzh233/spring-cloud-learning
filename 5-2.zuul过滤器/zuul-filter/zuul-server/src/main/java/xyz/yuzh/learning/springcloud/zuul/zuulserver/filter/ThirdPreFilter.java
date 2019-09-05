package xyz.yuzh.learning.springcloud.zuul.zuulserver.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author Harry Zhang
 * @since 2019-09-05 16:20
 */
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
