
# Hystrix Dashboard

> Hystrix-dashboard 是一款针对 Hystrix 进行实时监控的工具，通过 Hystrix Dashboard 我们可以在直观地看到各 Hystrix Command 的请求响应时间, 请求成功率等数据。

项目结构说明：


    └── hystrix-dashborad       父工程 POM
        ├── dashboard           9000，仪表盘服务
        ├── eureka-server       8761，注册中心
        ├── hello-service       8080，暴露给外部调用的服务
        └── provider-service    8081，内部服务


# 监控单个应用的 Hystrix 调用情况

### provider-service 提供内部接口

```java
@SpringBootApplication
@EnableDiscoveryClient
public class ProviderServiceApplication {}

@RestController
public class TestController {

    @GetMapping("/getData")
    public List<String> getData() {
        List<String> list = new ArrayList<>();
        list.add("first blood!");
        list.add("double kill!");
        list.add("triple kill!");
        list.add("ultra kill!");
        list.add("penta kill!");
        return list;
    }

}
```

### hello-service 外部调用

1. 依赖：

```xml
<dependencies>
    <!--    hystrix dashboard 需要端口支撑，所以需要添加 actuator 依赖。    -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <!--    服务间调用    -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>

    <!--    Feign 使用了 Hystrix    -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    </dependency>

    <!--    服务客户端    -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
</dependencies>
```

2. application.yml

```yml
eureka:
    ......

# feign 开启 hystrix
feign:
  hystrix:
    enabled: true

# 开启 hystrix 监控
management:
#  security:
#    enabled: false
  endpoints:
    web:
      exposure:
        include: hystrix.stream

# ribbon 超时时间设置（ms）
ribbon:
  ConnectTimeout: 3000
  ReadTimeout: 3000
  MaxAutoRetries: 0
  MaxAutoRetriesNextServer: 0

# hystrix 超时时间设置（ms）
hystrix:
  command:
    default:
      execution:
        timeout:
        isolation:
          thread:
            timeoutInMilliseconds: 5000
```

3. 主配置类

```java
@EnableHystrix
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class HelloServiceApplication {}
```

4. 服务间调用

```java
// --------------------------------------------------------------------------------------   TestProviderServiceController
@RestController
public class TestProviderServiceController {

    @Autowired
    private ProviderService providerService;

    @GetMapping("/getData")
    public List<String> getData() {
        return providerService.getProviderData();
    }

}

// --------------------------------------------------------------------------------------   ProviderService
@FeignClient(name = "provider-service", fallback = ProviderServiceFallback.class)
public interface ProviderService {

    @GetMapping("/getData")
    List<String> getProviderData();

}
```

### Dashboard 监控 Hystrix 的状态

1. 依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
</dependencies>
```

2. 配置

```yml
server:
  port: 9000
spring:
  application:
    name: hystrix-dashboard

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
  instance:
    prefer-ip-address: true
```

3. 主配置类

```java
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
public class DashboardApplication {}
```

### 测试

**1). 开启 hello-service，不开启 provider-service，不开启 dashboard，服务调用失败回调 fallback 方法（正常）：**

![](http://img.yuzh.xyz/20190903112130_7HegyF_Screenshot.png)

**2). 开启 dashboard，访问：http://192.168.0.103:9000/hystrix， 看到如下（正常）：**

![](http://img.yuzh.xyz/20190903112241_TK53cE_Screenshot.png)

    三种监控模式：默认的集群模式、指定集群模式、单个应用模式
    Cluster via Turbine (default cluster): http://turbine-hostname:port/turbine.stream
    Cluster via Turbine (custom cluster): http://turbine-hostname:port/turbine.stream?cluster=[clusterName]
    Single Hystrix App: http://hystrix-app:port/hystrix.stream

**3). 我们只需监控单个应用，输入第三种方式的链接：http://localhost:8080/actuator/hystrix.stream, 这代表监控名为「hello-service」的服务。**

![](http://img.yuzh.xyz/20190903112750_JMJ937_Screenshot.png)

**4). 访问几次 hello-service 的服务之后查看监控面板：**

![](http://img.yuzh.xyz/20190903114919_jagxRV_Screenshot.png)

**5). 开启 provider-service，再次访问 hello-service 的服务查看监控面板：**

![](http://img.yuzh.xyz/20190903113420_UOhk0z_Screenshot.png)


# 监控集群下的 Hystrix 调用情况

> 前面搭建了一个简单的的 Hystrix Dashboard，但是单单只使用 Hystrix Dashboard 的话, 只能看到单个应用内的服务信息, 这明显不够。 我们需要一个工具能让我们汇总系统内多个服务的数据并显示到 Hystrix Dashboard 上, 这个工具就是 Turbine。

### hello-service 添加一个内部服务接口

```java
@RestController
public class TestController {

    @GetMapping("/getHelloServiceData")
    public String getHelloServiceData() {
        return "Hi, this is hello service!";
    }

}
```


### 改造 provider-service

- 使其能够调用 hello-service 的服务
- 使其能够被 Hytrix Dashboard 监控

1. 在 pom 添加：

```xml
<dependencies>
    <!--    需要调用 hello-service 服务，故添加 feign 依赖    -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <!--    需要在服务调用时加入熔断器，添加 hystrix    -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    </dependency>
    <!--    整合 Hystrix Board 需要添加 actuator    -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    ......
</dependencies>
```

2. applicatin.yml

```yml
spring:
    ......
eureka:
    ......
# -----------------------------------------------------------------------
# 开启 hystrix 监控
management:
  endpoints:
    web:
      exposure:
        include: hystrix.stream

# feign 开启 hystrix
feign:
  hystrix:
    enabled: true

# ribbon 超时时间设置（ms）
ribbon:
  ConnectTimeout: 3000
  ReadTimeout: 3000
  MaxAutoRetries: 0
  MaxAutoRetriesNextServer: 0

# hystrix 超时时间设置（ms）
hystrix:
  command:
    default:
      execution:
        timeout:
          isolation:
            thread:
              timeoutInMilliseconds: 5000
```

3. 主配置类

```java
@EnableHystrix
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ProviderServiceApplication {}
```

4. 调用 hello-service 服务

```java
@RestController
public class TestHelloServiceController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/getHelloServiceData")
    public String getHelloServiceData() {
        return helloService.getHelloServiceData();
    }

}

@FeignClient(name = "hello-service", fallback = HelloServiceFallback.class)
public interface HelloService {

    @GetMapping("/getHelloServiceData")
    String getHelloServiceData();

}
```

### 测试 provider-service 的 hystrix 监控状态

- **prodiver-service 应该能够调用 hello-service**
![](http://img.yuzh.xyz/20190903151339_9iQCG4_Screenshot.png)

- **prodiver-service 熔断器应该起作用**
![](http://img.yuzh.xyz/20190903151019_TqbeCZ_Screenshot.png)

- **hystrix-dashboard 应该能够监控 prodiver-service 的 hytrix 状态**
![](http://img.yuzh.xyz/20190903151056_9nmKYZ_Screenshot.png)

### 搭建 Turbine 项目，集群监控 hello-service 和 provider-service

1. pom

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-turbine</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
</dependencies>
```

2. application.yml

```yml
spring:
  application:
    name: hystrix-dashboard-turbine

server:
  port: 9088

eureka:
  client:
    fetch-registry: true
    register-with-eureka: true
    service-url:
      default-zone: http://localhost:8761/eureka/
  instance:
    prefer-ip-address: true

# hystrix dashboard 集群监控
turbine:
  app-config: hello-service, provider-service
  cluster-name-expression: "'default'"
```

3. 主配置类

```java
@EnableTurbine
@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrixDashboard
public class TurbineApplication {}
```

4. 访问 http://localhost:8081/getHelloServiceData， provider-service 调用 hello-service
5. 访问 http://192.168.0.103:8080/getData， hello-service 调用 provider-service:
![](http://img.yuzh.xyz/20190903154523_LaahzV_Screenshot.png)

6. 访问 http://192.168.0.103:9088/hystrix, 查看监控面板:
![](http://img.yuzh.xyz/20190903154904_v6AzhX_Screenshot.png)
