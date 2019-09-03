package xyz.yuzh.learning.springcloud.hystrix.providerservice.feignclient;

import org.springframework.stereotype.Component;

/**
 * @author Harry Zhang
 * @since 2019-09-03 14:28
 */
@Component
public class HelloServiceFallback implements HelloService {

    @Override
    public String getHelloServiceData() {
        return "can not get data from hello-service!";
    }

}
