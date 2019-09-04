package xyz.yuzh.learning.springcloud.hystrix.feignconsumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.yuzh.learning.springcloud.hystrix.feignconsumer.service.ExceptionHandlerService;

import javax.annotation.Resource;

/**
 * @author Harry Zhang
 * @since 2019-09-03 17:36
 */
@RestController
public class TestController {

    @Resource
    private ExceptionHandlerService exceptionHandlerService;

    @GetMapping("/testHystrixBadRequestException")
    public String testHystrixBadRequestException() {
        return exceptionHandlerService.testHystrixBadRequestException("11");
    }

}
