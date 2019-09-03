package xyz.yuzh.learning.springcloud.hystrix.providerservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Harry Zhang
 * @since 2019-09-03 14:25
 */
@FeignClient(name = "hello-service", fallback = HelloServiceFallback.class)
public interface HelloService {

    @GetMapping("/getHelloServiceData")
    String getHelloServiceData();

}
