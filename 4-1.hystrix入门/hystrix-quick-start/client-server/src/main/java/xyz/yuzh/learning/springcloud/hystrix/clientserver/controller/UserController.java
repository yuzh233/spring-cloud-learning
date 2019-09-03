package xyz.yuzh.learning.springcloud.hystrix.clientserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.yuzh.learning.springcloud.hystrix.clientserver.service.UserService;

/**
 * @author Harry Zhang
 * @since 2019-09-02 16:57
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUser/{username}")
    public String getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

}
