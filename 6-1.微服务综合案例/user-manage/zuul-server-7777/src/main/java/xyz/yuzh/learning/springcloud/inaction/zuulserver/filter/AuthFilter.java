package xyz.yuzh.learning.springcloud.inaction.zuulserver.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.yuzh.learning.springcloud.inaction.comm.exception.BaseException;
import xyz.yuzh.learning.springcloud.inaction.comm.exception.BaseExceptionBody;
import xyz.yuzh.learning.springcloud.inaction.comm.exception.CommonError;
import xyz.yuzh.learning.springcloud.inaction.comm.vo.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 鉴权filter
 *
 * @author Harry
 */
@Component
public class AuthFilter extends ZuulFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public boolean shouldFilter() {
        // 判断是否需要进行处理
        return true;
    }

    @Override
    public Object run() {
        RequestContext rc = RequestContext.getCurrentContext();
        authUser(rc);
        return null;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }


    /**
     * 如果请求头没带 x-customs-user 不允调用后续服务
     */
    private static void authUser(RequestContext ctx) {
        HttpServletRequest request = ctx.getRequest();
        Map<String, String> header = httpRequestToMap(request);
        String userId = header.get(User.CONTEXT_KEY_USER_ID);
        if (StringUtils.isEmpty(userId)) {
            try {
                BaseException BaseException = new BaseException(CommonError.AUTH_EMPTY_ERROR.getCode(), CommonError.AUTH_EMPTY_ERROR.getCodeEn(), CommonError.AUTH_EMPTY_ERROR.getMessage(), 1L);
                BaseExceptionBody errorBody = new BaseExceptionBody(BaseException);
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(401);
                ctx.setResponseBody(JSONObject.toJSON(errorBody).toString());
            } catch (Exception e) {
                logger.error("println message error", e);
            }
        } else {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                ctx.addZuulRequestHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    private static Map<String, String> httpRequestToMap(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;
    }

}
