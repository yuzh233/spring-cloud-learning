package xyz.yuzh.learning.springcloud.zuul.clienta.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Harry Zhang
 * @since 2019-09-04 11:57
 */
@RestController
public class TestController {

    @GetMapping("/add")
    public Integer add(Integer a, Integer b) {
        return a + b;
    }


    /**
     * 测试：zuul 中如果配置了 ignored-patterns: /test/**  此接口将不能通过 zuul 调通
     */
    @GetMapping("/client/test/aaaa")
    public String test(Integer a, Integer b) {
        return "/client/test/aaaa 接口：" + (a + b);
    }


    /**
     * 测试：zuul 的敏感头传递
     */
    @GetMapping("/header")
    public Map<String, String> header(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("Cookie", request.getHeader("Cookie"));
        map.put("Set-Cookie", request.getHeader("Set-Cookie"));
        map.put("Authorization", request.getHeader("Authorization"));
        map.put("Can-Read", request.getHeader("Can-Read"));
        return map;
    }

}
