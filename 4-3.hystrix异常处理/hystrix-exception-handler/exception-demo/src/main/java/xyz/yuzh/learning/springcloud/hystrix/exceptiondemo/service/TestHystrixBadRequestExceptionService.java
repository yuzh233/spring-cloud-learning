package xyz.yuzh.learning.springcloud.hystrix.exceptiondemo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 添加注解实现服务降级
 *
 * @author Harry Zhang
 * @since 2019-09-03 16:55
 */
@Service
public class TestHystrixBadRequestExceptionService {

    private static final Logger log = LoggerFactory.getLogger(TestHystrixBadRequestExceptionService.class);

    @HystrixCommand(fallbackMethod = "testHystrixBadRequestExceptionFallback")
    public String testHystrixBadRequestException() {
        throw new HystrixBadRequestException("Parameter error exception occurred!");
    }

    /**
     * 服务降级回调方法
     *
     * @param throwable 获得调用失败异常
     */
    public String testHystrixBadRequestExceptionFallback(Throwable throwable) {
        log.error("Parameter error exception occurred!", throwable);
        return "This is the callback method after a parameter error exception occurs";
    }

}
