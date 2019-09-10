package xyz.yuzh.learning.springcloud.inaction.comm.util;

import org.springframework.util.StringUtils;
import xyz.yuzh.learning.springcloud.inaction.comm.vo.User;

import javax.servlet.http.HttpServletResponse;

public class AuthUtil {

    public static boolean authUser(User user, HttpServletResponse respone) throws Exception {
        if (StringUtils.isEmpty(user.getUserId())) {
            return false;
        } else {
            return true;
        }
    }

}
