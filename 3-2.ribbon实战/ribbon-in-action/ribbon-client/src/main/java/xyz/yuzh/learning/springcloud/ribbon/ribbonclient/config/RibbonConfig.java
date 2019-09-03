package xyz.yuzh.learning.springcloud.ribbon.ribbonclient.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 针对全部服务的 ribbon 负载均衡策略
 *
 * @author Harry Zhang
 * @since 2019-09-02 10:06
 */
@Configuration
public class RibbonConfig {

    /**
     * 全局配置 ribbon 负载均衡策略
     */
    @Bean
    public IRule ribbonRule() {
        // 随机策略
        return new RandomRule();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
