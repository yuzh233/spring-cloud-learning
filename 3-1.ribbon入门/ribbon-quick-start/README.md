
# Ribbon 的入门案例

项目结构如下：

```
└── ribbon-quick-start # 项目父工程，用于统一管理 Spring Cloud 版本以及公共的依赖。
    ├── client-a       # ribbon-client 项目，节点 a。服务提供者，端口为 8080，为测试 Ribbon 提供接口。
    ├── client-b       # ribbon-client 项目，节点 b。服务提供者，端口为 8081，为测试 Ribbon 提供接口。
    ├── eureka         # 注册中心
    ├── ribbon-client  # ribbon 客户端，服务消费者。
```

ribbon-client 项目提供接口：

```java
@RestController
public class TestController {

    @GetMapping("/add")
    public String add(Integer a, Integer b, HttpServletRequest request) {
        return "From Port:" + request.getServerPort() + ", Result: " + (a + b);
    }

}
```

测试 Ribbon 负载均衡：

```java
@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/add")
    public String test(Integer a, Integer b) {
        /*
            测试客户端负载均衡
            ribbon-client 有两个节点：client-a 和 client-b
         */
        return restTemplate.getForObject("http://ribbon-client/add?a=" + a + "&b=" + b, String.class);
    }

}
```
