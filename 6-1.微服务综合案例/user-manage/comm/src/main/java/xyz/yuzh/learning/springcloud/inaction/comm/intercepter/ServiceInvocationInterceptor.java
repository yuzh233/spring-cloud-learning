package xyz.yuzh.learning.springcloud.inaction.comm.intercepter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Harry Zhang
 * @since 2019-09-10 22:09
 */
public class ServiceInvocationInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ServiceInvocationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String xServiceCall = request.getHeader("x-service-call");
        if (null == xServiceCall) {
            log.info("服务调用失败（只能由网关调用！）");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
