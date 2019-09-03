package xyz.yuzh.learning.springcloud.hystrix.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign 服务间调用
 *
 * @author Harry Zhang
 * @since 2019-09-02 17:28
 */
@FeignClient(name = "service-provider", fallback = UserServiceFallback.class)
public interface UserService {

    @GetMapping(value = "/getUser")
    String getUser(@RequestParam("username") String username);

}
