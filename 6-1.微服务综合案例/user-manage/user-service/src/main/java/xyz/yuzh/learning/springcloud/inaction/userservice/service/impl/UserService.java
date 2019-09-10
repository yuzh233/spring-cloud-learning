package xyz.yuzh.learning.springcloud.inaction.userservice.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import xyz.yuzh.learning.springcloud.inaction.userservice.service.IUserService;
import xyz.yuzh.learning.springcloud.inaction.userservice.service.dataservice.DataService;

import java.util.List;

/**
 * @author Harry
 */
@Component
public class UserService implements IUserService {

    @Autowired
    private DataService dataService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getDefaultUser() {
        return dataService.getDefaultUser();
    }

    @Override
    public String getContextUserId() {
        return dataService.getContextUserId();
    }

    @Override
    public List<String> getProviderData() {
        return restTemplate.getForObject("http://data-service/getProviderData", List.class);
    }
}
