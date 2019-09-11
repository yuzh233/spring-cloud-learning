package xyz.yuzh.learning.springcloud.configclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Harry Zhang
 * @since 2019-09-11 16:54
 */
@Component
@ConfigurationProperties("xyz.yuzh.learning.springcloud")
public class ApplicationProperties {

    private String testConfig;


    public String getTestConfig() {
        return testConfig;
    }

    public void setTestConfig(String testConfig) {
        this.testConfig = testConfig;
    }
}
