package xyz.yuzh.learning.springcloud.hystrix.feignconsumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Harry Zhang
 * @since 2019-09-03 17:36
 */
@FeignClient(name = "hystrix-exception-handle")
public interface ExceptionHandlerService {

    @GetMapping("/testHystrixBadRequestException")
    String testHystrixBadRequestException(String username);

}
