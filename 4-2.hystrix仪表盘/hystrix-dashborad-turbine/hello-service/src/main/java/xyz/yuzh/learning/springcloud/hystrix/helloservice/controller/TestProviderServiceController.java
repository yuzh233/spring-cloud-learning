package xyz.yuzh.learning.springcloud.hystrix.helloservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.yuzh.learning.springcloud.hystrix.helloservice.service.ProviderService;

import java.util.List;

/**
 * @author Harry Zhang
 * @since 2019-09-02 21:23
 */
@RestController
public class TestProviderServiceController {

    @Autowired
    private ProviderService providerService;

    @GetMapping("/getData")
    public List<String> getData() {
        return providerService.getProviderData();
    }

}
