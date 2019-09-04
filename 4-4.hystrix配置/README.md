
# Hystrix 配置

隔离策略，HystrixCommandKey，如果不配置，则默认为方法名：

    默认值：THREAD
    默认属性：hystrix.command.defualt.execution.isolation.strategy
    实例属性：hystrix.command.HystrixCommandKey.execution.isolation.strategy

配置 hystrixCommand 命令执行超时时间，以毫秒为单位：

    默认值：1000
    默认属性：hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds
    实例属性：hystrix.command.HystrixCommandKey.execution.isolation.thread.timeoutInMilliseconds

hystrixCommand 命令执行是否开启超时：

    默认值：true
    默认属性：hystrix.command.default.execution.timeout.enabled
    实例属性：hystrix.command.HystrixCommandKey.execution.timeout.enabled

超时时是否应该中断执行操作：

    默认值：10
    默认属性：hystrix.command.default.execution.isolation.thread.interruptOnTimeout
    实例属性：hystrix.command.HystrixCommandKey.execution.isolation.thread.interruptOnTimeout

信号量请求数，当设置为信号量隔离策略时，设置最大允许的请求数：

    默认值：10
    默认属性：hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests
    实例属性：hystrix.command.HystrixCommandKey.execution.isolation.semaphore.maxConcurrentRequests

Circuit Breker 设置打开 fallback 并启动 fallback 逻辑的错误比率：

    默认值：50
    默认属性：hystrix.command.default.circuitBreaker.errorThresholdPercentage
    实例属性：hystrix.command.HystrixCommandKey.circuitBreaker.errorThresholdPercentage

强制打开断路器，拒绝所有请求：

    默认值：false
    默认属性：hystrix.command.default.circuitBreaker.forceOpen
    实例属性：hystrix.command.HystrixCommandKey.circuitBreaker.forceOpen

当为线程隔离时，线程池核心大小：

    默认值：10
    默认属性：hystrix.threadpool.default.coreSize
    实例属性：hystrix.threadpool.HystrixCommandKey.coreSize

当 Hystrix 隔离策略为线程池隔离模式时，最大线程池大小的配置如下，在 1.5.9 版本中还需要配置 allowMaximumSizeToDivergeFromCoreSize 为 true

    默认值：10
    默认属性：hystrix.threadpool.default.maximumSize
    实例属性：hystrix.threadpool.HystrixCommandKey.maximumSize

allowMaximumSizeToDivergeFromCoreSize，此属性允许 maximumSize 生效：

    默认值：false
    默认属性：hystrix.threadpool.default.allowMaximumSizeToDivergeFromCoreSize
    实例属性：hystrix.threadpool.HystrixCommandKey.allowMaximumSizeToDivergeFromCoreSize

> 在真实应用中，一般都会对超时时间、线程池大小、信号量等进行修改，具体要结合业务进行分析，默认 Hystrix 的超时时间为 1 秒，但在实际运用过程中。发现 1 秒有些过短，通常会设置 5～10 秒左右。如果配置了 Ribbon 的时间，其超过时间需要和 Ribbon 的时间配合使用，一般 Ribbon 的时间应短于 Hystrix 的超时时间。
