package xyz.yuzh.learning.springcloud.hystrix.consumer.service;

import org.springframework.stereotype.Component;

/**
 * Hystrix 调用失败回调方法类
 *
 * @author Harry Zhang
 * @since 2019-09-02 17:29
 */
@Component
public class UserServiceFallback implements UserService{

    /**
     * 与服务调用方法保持同名 @link{xyz.yuzh.learning.springcloud.hystrix.consumer.service.UserService#getUser()}
     */
    @Override
    public String getUser(String username) {
        return "The user dose not exist in this system!";
    }

}
