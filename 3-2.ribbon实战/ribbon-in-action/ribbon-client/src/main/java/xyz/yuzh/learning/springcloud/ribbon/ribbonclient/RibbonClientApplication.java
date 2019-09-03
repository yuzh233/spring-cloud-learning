package xyz.yuzh.learning.springcloud.ribbon.ribbonclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import xyz.yuzh.learning.springcloud.ribbon.ribbonclient.annotation.AvoidScan;
import xyz.yuzh.learning.springcloud.ribbon.ribbonclient.config.SpecialRibbonConfig;

/**
 * - @RibbonClients：对于名为 ribbon-client 的服务，使用特定的 ribbon 配置。
 * - @ComponentScan：忽略某个配置类
 */
@EnableDiscoveryClient
@SpringBootApplication
//@RibbonClients({
//        @RibbonClient(name = "ribbon-client", configuration = SpecialRibbonConfig.class)
//})
//@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {AvoidScan.class})})
public class RibbonClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RibbonClientApplication.class, args);
    }

}
