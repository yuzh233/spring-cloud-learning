package xyz.yuzh.learning.springcloud.hystrix.providerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.yuzh.learning.springcloud.hystrix.providerservice.feignclient.HelloService;

/**
 * @author Harry Zhang
 * @since 2019-09-03 14:23
 */
@RestController
public class TestHelloServiceController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/getHelloServiceData")
    public String getHelloServiceData() {
        return helloService.getHelloServiceData();
    }

}
