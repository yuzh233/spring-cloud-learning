package xyz.yuzh.learning.springcloud.inaction.dataservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 动态获取配置信息
 */
@Data
@Component
@ConfigurationProperties(prefix = "xyz.yuzh.learning.springcloud")
public class DataConfig {

    private String defaultUser;

}
