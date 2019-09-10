package xyz.yuzh.learning.springcloud.inaction.comm.intercepter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.yuzh.learning.springcloud.inaction.comm.context.UserContextHolder;
import xyz.yuzh.learning.springcloud.inaction.comm.util.HttpConvertUtil;
import xyz.yuzh.learning.springcloud.inaction.comm.vo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用控制层拦截器
 *
 * @author Harry
 */
public class UserContextInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(UserContextInterceptor.class);

    /**
     * 请求之前过滤：如果请求头没有用户信息将不能调用服务
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) {
        User user = new User(HttpConvertUtil.httpRequestToMap(request));
        if (StringUtils.isEmpty(user.getUserId()) && StringUtils.isEmpty(user.getUserName())) {
            log.error("the user is null, please access from gateway or check user info");
            return false;
        }
        // 请求头带了用户信息，将其存入 ThreadLocal.
        UserContextHolder.set(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3) {
        // 一个请求结束，删除 ThreadLocal.
        UserContextHolder.shutdown();
    }


}
