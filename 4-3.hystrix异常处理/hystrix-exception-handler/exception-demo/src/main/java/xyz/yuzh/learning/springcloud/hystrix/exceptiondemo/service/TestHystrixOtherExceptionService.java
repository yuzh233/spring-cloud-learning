package xyz.yuzh.learning.springcloud.hystrix.exceptiondemo.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 继承 HystrixCommand 方式实现服务降级
 * 使用这种方式每次都需要创建一个对象，不然会抛出异常。
 *
 * @author Harry Zhang
 * @since 2019-09-03 16:58
 */
public class TestHystrixOtherExceptionService extends HystrixCommand<String> {

    private static final Logger log = LoggerFactory.getLogger(TestHystrixOtherExceptionService.class);

    public TestHystrixOtherExceptionService() {
        super(HystrixCommandGroupKey.Factory.asKey("TestCommand"));
    }

    @Override
    protected String run() throws Exception {
        throw new RuntimeException("Nonparametric exception occurs!");
    }

    @Override
    protected String getFallback() {
        log.error("Nonparametric exception occurs!", getFailedExecutionException());
        return "This is the callback method after a nonparametric error exception occurs";
    }

}
