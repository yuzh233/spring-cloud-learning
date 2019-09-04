package xyz.yuzh.learning.springcloud.hystrix.exceptiondemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.yuzh.learning.springcloud.hystrix.exceptiondemo.service.TestHystrixBadRequestExceptionService;
import xyz.yuzh.learning.springcloud.hystrix.exceptiondemo.service.TestHystrixOtherExceptionService;

/**
 * @author Harry Zhang
 * @since 2019-09-03 16:37
 */
@RestController
public class TessExceptionController {

    @Autowired
    private TestHystrixBadRequestExceptionService testHystrixBadRequestExceptionService;


    /**
     * 测试：Hystrix 参数异常不会走 fallback
     */
    @GetMapping("/testHystrixBadRequestException")
    public String testHystrixBadRequestException() {
        return testHystrixBadRequestExceptionService.testHystrixBadRequestException();
    }


    /**
     * 测试：Hystrix 非参数异常会走 fallback
     */
    @GetMapping("/testHystrixOtherException")
    public String testHystrixOtherException() {
        return new TestHystrixOtherExceptionService().execute();
    }

}
