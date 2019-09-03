package xyz.yuzh.learning.springcloud.hystrix.providerservice.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Harry Zhang
 * @since 2019-09-02 21:19
 */
@Configuration
public class FeignConfig {


    /**
     * 启用负载均衡
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    /**
     * 负载均衡策略
     */
    @Bean
    public IRule rule() {
        return new RandomRule();
    }

}
