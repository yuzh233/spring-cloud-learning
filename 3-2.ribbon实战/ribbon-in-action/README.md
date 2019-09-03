
# 了解 Ribbon 相关配置

## 负载均衡策略和自定义配置

ribbon 的负载均衡策略：

策略类                   | 命令             | 描述
-------------------------|------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
RandomRule               | 随机策略         | 随机选择 Server
RoundRobinRule           | 轮询策略         | 按顺序循环选择 server
RetryRule                | 重试策略         | 在一个配置时间段内当选择的 server 不成功，则会一直尝试选择一个可用的 server。
RestAvailableRule        | 最低并发策略     | 逐个考察server，如果server断路器打开，则忽略，再选择其中并发连接最低的server
AvailabilityFilterRule    | 可用过滤策略     | 过滤掉一直连接失败并被标记为 circuit tripped 的server，过滤掉那些高并发连接的server
ResponseTimeWeightedRule | 响应时间加权策略 | 根据server的响应时间分配权重。相应时间越长，权重越低，被选择到的概率就越低；响应时间越短权重越高，被选择到的概率越高。这个策略很贴切，综合了各种因素，如：网络、磁盘、IO等。
ZoneAvoidanceRule        | 区域权衡策略     | 综合判断server所在区域的性能和server 的可用性轮询选择server，并且判定一个 AWS Zone的运行性能是否可用，剔除不可用的 Zone 的所有 server

### 配置全局配置负载均衡策略

```java
@Configuration
public class RibbonConfig {

    /**
     * 全局配置 ribbon 负载均衡策略
     */
    @Bean
    public IRule ribbonRule() {
        return new RandomRule();
    }
    
}
```

### 配置注解配置指定某些服务的负载均衡策略

1). 自定义注解 @AvoidScan

2). 特定的配置类

```java
/**
 * 针对某个服务指定 ribbon 负载均衡策略
 * <p>
 * - @AvoidScan: 自定义注解，辅以主配置类起作用。
 */
@AvoidScan
@Configuration
public class SpecialRibbonConfig {

    /**
     * 客户端配置管理器
     */
    @Autowired
    private IClientConfig config;

    @Bean
    public IRule robbinRule(IClientConfig config) {
        // 轮询策略
        return new RoundRobinRule();
    }

}
```

3). 主配置类添加 @RibbonClients 注解

```java
/**
 * - @RibbonClients：对于名为 ribbon-client 的服务，使用特定的 ribbon 配置。
 * - @ComponentScan：忽略某个配置类，这里选择忽略被 AvoidScan 标记的配置类，原因是已有一个 ribbon 配置类，不忽略会报错。
 */
@EnableDiscoveryClient
@SpringBootApplication
@RibbonClients({
        @RibbonClient(name = "ribbon-client", configuration = SpecialRibbonConfig.class)
})
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {AvoidScan.class})})
public class RibbonClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RibbonClientApplication.class, args);
    }

}
```

### 配置文件配置 ribbon 策略，推荐

```yml
# 指定 ribbon-client 服务的负载均衡策略
ribbon-client:
  ribbon:
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule
```

## 超时与重试

F 版中 ribbon 重试机制默认开启

局部配置：

```yml
# 配置名为 ribbon-client 服务的调用超时时间。
ribbon-client:
  ribbon:
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule
    ConnetTimeout: 30000
    ReadTimeout: 30000
    # 对第一次请求的服务的重试次数
    MaxAutoRetries: 1
    # 要重试的下一个服务的最大数量（不包括第一个服务）
    MaxAutoRetriesNextServer: 1
    OkToRetryOnAllOperations: true
```

全局配置：

```yml
ribbon:
  ConnetTimeout: 3000
  ReadTimeout: 3000
```


## 饥饿加载

```yml
ribbon:
  eager-load:
    # 开启饥饿加载：应用启动时加载所有配置项的应用程序上下文
    enabled: true
    # 需要被加载的服务，逗号分割。
    clients: ribbon-client
```

## ribbon 脱离 eureka 使用

```yml
ribbon:
  ......
  
  eureka:
    # 禁用 eureka 注册中心，手动指定服务地址，配置在：client:ribbon:listOfServers
    enabled: false

client:
  ribbon:
    # 手动指定服务地址
    listOfServers: http://localhost:8081, http://localhost:8080
```
