package xyz.yuzh.learning.springcloud.zuul.serviceprovider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Harry Zhang
 * @since 2019-09-06 12:03
 */
@RestController
public class TestController {

    @GetMapping("/getDataFromProvider")
    public String getDataFromProvider() {
        return "this is data from provider!";
    }

}
