package xyz.yuzh.learning.springcloud.hystrix.helloservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Harry Zhang
 * @since 2019-09-03 14:22
 */
@RestController
public class TestController {

    @GetMapping("/getHelloServiceData")
    public String getHelloServiceData() {
        return "Hi, this is hello service!";
    }

}
