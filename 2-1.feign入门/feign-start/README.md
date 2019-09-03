
# Feign 服务间调用

## feign 超时时间设置

```yml
feign:
  client:
    config:
      default:
        # 建立连接的时间
        connectTimeout: 5000
        # 读取资源的时间
        readTimeout: 5000
        # 日志级别
        loggerLever: basic
```

feign 的调用分两层：Ribbon 的调用和 Hystrix 的调用，高版本的 Hystrix 的默认关闭的。

ribbon 超时时间配置：

```yml
ribbon:
  ReadTimeout: 1000
  ConnectTimeout: 1000
```

hystrix 超时时间配置：

```yml
frign:
  hystrix:
    enabled: true
        
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 1000
```