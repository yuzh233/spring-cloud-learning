package xyz.yuzh.learning.springcloud.inaction.dataservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 动态获取配置信息
 */
@Component
@ConfigurationProperties(prefix = "xyz.yuzh.learning.springcloud")
public class DataConfig {

    private String defaultUser;

    public String getDefaultUser() {
        return defaultUser;
    }

    public void setDefaultUser(String defaultUser) {
        this.defaultUser = defaultUser;
    }
}
