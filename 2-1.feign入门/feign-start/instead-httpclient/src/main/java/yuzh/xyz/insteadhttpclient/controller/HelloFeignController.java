package yuzh.xyz.insteadhttpclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yuzh.xyz.insteadhttpclient.service.HelloFeignService;

/**
 * @author Harry
 * @since 2019-07-17 15:45
 */
@RestController
public class HelloFeignController {

    private HelloFeignService helloFeignService;

    HelloFeignController(HelloFeignService helloFeignService) {
        this.helloFeignService = helloFeignService;
    }

    /**
     * 服务消费者对位提供的服务
     */
    @GetMapping(value = "/search/github")
    public String searchGithubRepoByStr(@RequestParam("str") String queryStr) {
        return helloFeignService.searchRepo(queryStr);
    }

}

