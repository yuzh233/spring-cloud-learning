package yuzh.xyz.provider.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yuzh.xyz.provider.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Feign 多参数调用测试
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser(User user, HttpServletRequest request) {
        String token = request.getHeader("oauthToken");
        System.out.println("token: " + token);
        System.out.println("user: " + user);
        return "{new} hello," + user.getName();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateUser(@RequestBody User user) {
        System.out.println("user: " + user);
        return "{update} hello," + user.getName();
    }

}
