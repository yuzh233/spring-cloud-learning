package xyz.yuzh.learning.springcloud.inaction.userservice.service;


import java.util.List;

/**
 * @author Harry
 */
public interface IUserService {

    /**
     * 通过 Feign 调用 data-service 的接口数据
     */
    String getDefaultUser();

    /**
     * 通过 Feign 调用 data-service 的接口数据
     */
    String getContextUserId();

    /**
     * 通过 RestTemplate 调用 data-service 的接口数据
     */
    List<String> getProviderData();

}
