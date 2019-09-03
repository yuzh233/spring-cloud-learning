package yuzh.xyz.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import yuzh.xyz.consumer.model.User;

/**
 * @author Harry
 * @since 2019-07-17 17:14
 */
@FeignClient(name = "provider")
public interface UserFeignService {


    @RequestMapping(value = "/feign", method = RequestMethod.GET)
    String helloFeign();

    /**
     * Feign 多参数 GET 调用
     */
    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    String addUser(User user);

    /**
     * Feign 多参数 POST 调用
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    String updateUser(@RequestBody User user);
}
