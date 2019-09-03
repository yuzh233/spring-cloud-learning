package xyz.yuzh.learning.springcloud.ribbon.clientb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Harry Zhang
 * @since 2019-08-30 16:28
 */
@RestController
public class TestController {

    @GetMapping("/add")
    public String add(Integer a, Integer b, HttpServletRequest request) throws InterruptedException {
        Thread.sleep(4000);
        return "From Port:" + request.getServerPort() + ", Result: " + (a + b);
    }

}
