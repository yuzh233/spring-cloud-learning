package xyz.yuzh.learning.springcloud.inaction.dataservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.yuzh.learning.springcloud.inaction.comm.context.UserContextHolder;
import xyz.yuzh.learning.springcloud.inaction.comm.intercepter.UserContextInterceptor;
import xyz.yuzh.learning.springcloud.inaction.dataservice.config.DataConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据控制器（提供给 user-service 调用）
 *
 * @author Harry
 */
@RestController
public class DataController {

    @Autowired
    private DataConfig dataConfig;


    /**
     * 从用户上下文获取用户信息
     * <p>
     * UserContextHolder 在请求进入之前存入信息 {@link UserContextInterceptor#preHandle}
     */
    @GetMapping("/getContextUserId")
    public String getContextUserId() {
        return UserContextHolder.currentUser().getUserId();
    }


    /**
     * 获得配置中心的配置属性
     */
    @GetMapping("/getDefaultUser")
    public String getDefaultUser() {
        return dataConfig.getDefaultUser();
    }


    /**
     * 静态数据
     */
    @GetMapping("/getProviderData")
    public List<String> getProviderData() {
        List<String> provider = new ArrayList<>();
        provider.add("Beijing Company");
        provider.add("Shanghai Company");
        provider.add("Shenzhen Company");
        return provider;
    }

}
