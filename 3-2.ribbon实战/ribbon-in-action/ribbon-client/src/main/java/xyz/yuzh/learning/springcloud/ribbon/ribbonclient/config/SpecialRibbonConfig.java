package xyz.yuzh.learning.springcloud.ribbon.ribbonclient.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.yuzh.learning.springcloud.ribbon.ribbonclient.annotation.AvoidScan;

/**
 * 针对某个服务指定 ribbon 负载均衡策略
 * <p>
 * - @AvoidScan: 自定义注解，辅以主配置类起作用。
 *
 * @author Harry Zhang
 * @since 2019-09-02 10:25
 */
@AvoidScan
//@Configuration
public class SpecialRibbonConfig {

    /**
     * 客户端配置管理器
     */
    @Autowired
    private IClientConfig config;

    @Bean
    public IRule robbinRule(IClientConfig config) {
        // 轮询策略
        return new RoundRobinRule();
    }

}
