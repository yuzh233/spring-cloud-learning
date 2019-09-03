package xyz.yuzh.learning.springcloud.ribbon.ribbonclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Harry Zhang
 * @since 2019-08-30 16:46
 */
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
