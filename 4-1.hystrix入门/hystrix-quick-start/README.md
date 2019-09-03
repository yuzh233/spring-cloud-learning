
# Hystrix 入门

> 演示项目：client-serer

1. 依赖：

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

2. 主配置类：

```java
@EnableHystrix
@SpringBootApplication
@EnableDiscoveryClient
public class ClientServerApplication {//......}
```

3. application.yml（无需特别配置，只需要配置 eureka 服务地址即可）:

```yml
spring:
  application:
    name: hystrix-demo

eureka:
  client:
    service-url:
      default-zone: http://localhost:8761/eureka/
```

4. 使用：

```java
@Service
public class UserService {

    @HystrixCommand(fallbackMethod = "defaultUser")
    public String getUser(String username) {
        if ("Spring".equals(username)) {
            return "this is real user!";
        } else {
            throw new RuntimeException("can not find user!");
        }
    }

    /**
     * 出错调用该方法
     */
    public String defaultUser(String username) {
        return "The user dose not exist in this system!";
    }

}
```

# Feign 中使用 Hystrix

老版本的 Feign 默认打开 Hystrix，新版本的 Feign 需要自己开启。

> 演示项目：consumer

1. 依赖：

```xml
<dependencies>
    <!--  需要注册到 eureka  -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <!--  Feign 中使用 Hystrix  -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <!--  Feign 中使用 Hystrix  -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    </dependency>
    <!--  启用 web  -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

2. application.yml

```yml
spring:
  application:
    name: service-consumer

feign:
  hystrix:
    enabled: true # 默认为 false，即：关闭 Hytrix
```

3. 控制层调用：

```java
@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUser/{username}")
    public String getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

}
```

4. Feign 服务调用接口：

```java
@FeignClient(name = "service-provider", fallback = UserServiceFallback.class)
public interface UserService {

    @GetMapping(value = "/getUser")
    String getUser(@RequestParam("username") String username);

}
```

5. Feign 调用失败 Hystrix 降级方法类：

```java
@Component
public class UserServiceFallback implements UserService{

    /**
     * 与服务调用方法保持同名 @link{xyz.yuzh.learning.springcloud.hystrix.consumer.service.UserService#getUser()}
     */
    @Override
    public String getUser(String username) {
        return "The user dose not exist in this system!";
    }

}
```

6. 测试

关闭 Feign 中的 Hystrix `feign.hystrix.enabled=false` 后调用一个没启动的服务（service-provider）后正常报错：
    
        Whitelabel Error Page
        This application has no explicit mapping for /error, so you are seeing this as a fallback.
        
        Mon Sep 02 17:41:42 CST 2019
        There was an unexpected error (type=Internal Server Error, status=500).
        com.netflix.client.ClientException: Load balancer does not have available server for client: service-provider
        

可以看到，hystrix 关闭后，服务调用失败没有进入到 fallback 指定的方法，此时将 Hystrix 打开 `feign.hystrix.enabled=true`，后调用：

http://127.0.0.1:8080/getUser/harry

        The user dose not exist in this system!
        
service-provider 服务没启动，调用失败之后进入了 fallback 指定的方法。