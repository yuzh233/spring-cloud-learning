package xyz.yuzh.learning.springcloud.hystrix.clientserver.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

/**
 * @author Harry Zhang
 * @since 2019-09-02 16:57
 */
@Service
public class UserService {

    @HystrixCommand(fallbackMethod = "defaultUser")
    public String getUser(String username) {
        if ("Spring".equals(username)) {
            return "this is real user!";
        } else {
            throw new RuntimeException("can not find user!");
        }
    }

    /**
     * 出错调用该方法
     */
    public String defaultUser(String username) {
        return "The user dose not exist in this system!";
    }

}
