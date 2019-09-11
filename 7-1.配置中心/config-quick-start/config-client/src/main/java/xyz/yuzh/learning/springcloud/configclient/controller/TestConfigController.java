package xyz.yuzh.learning.springcloud.configclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.yuzh.learning.springcloud.configclient.config.ApplicationProperties;

/**
 * @author Harry Zhang
 * @since 2019-09-11 16:56
 */
@RestController
public class TestConfigController {

    @Autowired
    private ApplicationProperties applicationProperties;

    @GetMapping("/getTestConfig")
    public String getTestConfig() {
        return applicationProperties.getTestConfig();
    }

}
