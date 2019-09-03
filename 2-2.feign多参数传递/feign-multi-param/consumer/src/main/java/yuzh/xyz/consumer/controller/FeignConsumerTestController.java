package yuzh.xyz.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yuzh.xyz.consumer.service.UserFeignService;

/**
 * @author Harry
 * @since 2019-07-17 17:16
 */
@RestController
public class FeignConsumerTestController {

    /**
     * 注入服务提供者,远程的Http服务
     */
    @Autowired
    private UserFeignService userFeignService;

    /**
     * 服务消费者对位提供的服务
     */
    @GetMapping("/consumer/feign")
    public ResponseEntity<String> findByIdByEurekaServer() {
        String result = userFeignService.helloFeign();
        return ResponseEntity.ok().body("「消费端调用」 " + result);
    }

}