package xyz.yuzh.learning.springcloud.zuul.zuulserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Harry Zhang
 * @since 2019-09-04 11:57
 */
@RestController
public class TestController {

    @GetMapping("/client")
    public String add(Integer a, Integer b) {
        return "zuul 接口：" + (a + b);
    }

}
