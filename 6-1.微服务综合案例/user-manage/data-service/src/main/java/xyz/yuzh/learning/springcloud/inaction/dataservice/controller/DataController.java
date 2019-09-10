package xyz.yuzh.learning.springcloud.inaction.dataservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.yuzh.learning.springcloud.inaction.comm.context.UserContextHolder;
import xyz.yuzh.learning.springcloud.inaction.dataservice.config.DataConfig;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@RestController
public class DataController {

    @Autowired
    private DataConfig dataConfig;

    @GetMapping("/getContextUserId")
    public String getContextUserId() {
        return UserContextHolder.currentUser().getUserId();
    }

    @GetMapping("/getDefaultUser")
    public String getDefaultUser() {
        return dataConfig.getDefaultUser();
    }

    @GetMapping("/getProviderData")
    public List<String> getProviderData() {
        List<String> provider = new ArrayList<>();
        provider.add("Beijing Company");
        provider.add("Shanghai Company");
        provider.add("Shenzhen Company");
        return provider;
    }

}
