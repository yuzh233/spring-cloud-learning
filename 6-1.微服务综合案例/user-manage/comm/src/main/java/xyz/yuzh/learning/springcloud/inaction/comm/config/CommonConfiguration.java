package xyz.yuzh.learning.springcloud.inaction.comm.config;

import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import xyz.yuzh.learning.springcloud.inaction.comm.context.SpringCloudHystrixConcurrencyStrategy;
import xyz.yuzh.learning.springcloud.inaction.comm.intercepter.FeignUserContextInterceptor;
import xyz.yuzh.learning.springcloud.inaction.comm.intercepter.RestTemplateUserContextInterceptor;
import xyz.yuzh.learning.springcloud.inaction.comm.intercepter.ServiceInvocationInterceptor;
import xyz.yuzh.learning.springcloud.inaction.comm.intercepter.UserContextInterceptor;

/**
 * 通用配置
 *
 * @author Harry
 */
@Configuration
@EnableWebMvc
public class CommonConfiguration extends WebMvcConfigurerAdapter {


    /**
     * 请求拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 没带 x-customs-user 头不允许调用
        registry.addInterceptor(new UserContextInterceptor());
        // 没带 x-service-call 头不允许调用（在网关层添加 x-service-call 请求头实现所有服务只能由网关调用）
        registry.addInterceptor(new ServiceInvocationInterceptor());
    }


    /**
     * 创建 Feign 请求拦截器，在发送请求前设置认证的用户上下文信息
     */
    @Bean
    @ConditionalOnClass(Feign.class)
    public FeignUserContextInterceptor feignTokenInterceptor() {
        return new FeignUserContextInterceptor();
    }


    /**
     * RestTemplate拦截器
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new RestTemplateUserContextInterceptor());
        return restTemplate;
    }

    @Bean
    public SpringCloudHystrixConcurrencyStrategy springCloudHystrixConcurrencyStrategy() {
        return new SpringCloudHystrixConcurrencyStrategy();
    }

}
