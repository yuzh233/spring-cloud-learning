package xyz.yuzh.learning.springcloud.hystrix.helloservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 调用 provider-service 服务的接口
 *
 * @author Harry Zhang
 * @since 2019-09-02 21:14
 */
@FeignClient(name = "provider-service", fallback = ProviderServiceFallback.class)
public interface ProviderService {

    @GetMapping("/getData")
    List<String> getProviderData();

}
