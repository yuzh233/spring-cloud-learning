package xyz.yuzh.learning.springcloud.hystrix.feignconsumer.service;

import org.springframework.stereotype.Component;

/**
 * @author Harry Zhang
 * @since 2019-09-03 17:42
 */
@Component
public class ExceptionHandlerServiceFallback implements ExceptionHandlerService {
    @Override
    public String testHystrixBadRequestException(String username) {
        return "testHystrixBadRequestException";
    }
}
