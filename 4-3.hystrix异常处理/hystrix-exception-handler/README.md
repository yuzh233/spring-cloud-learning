
# Hystrix 异常机制与处理

Hystrix 异常处理中有五种出错的情况会被 fallback 处理，分别是：

- failure：执行失败，抛出异常。
- timeout：执行超时。
- thread_pool_rejected：线程池拒绝。
- semaphore_rejected：信号量拒绝。

Hystrix 对于 BadException 异常是不会触发 fallback 的，会排除 HystrixBadRequestException。这种异常需要我们根据响应自己封装异常或做其他处理。

1. 测试接口

```java
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
```

2. 参数异常 service

```java
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
```

3. 非参数异常 service

```java
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
```

3. 测试

![](http://img.yuzh.xyz/20190903172827_rUEuJr_Screenshot.png)

