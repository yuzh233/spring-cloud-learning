package xyz.yuzh.learning.springcloud.inaction.userservice.service;


import java.util.List;

/**
 * @author Harry
 */
public interface IUserService {

    String getDefaultUser();

    String getContextUserId();

    List<String> getProviderData();

}
