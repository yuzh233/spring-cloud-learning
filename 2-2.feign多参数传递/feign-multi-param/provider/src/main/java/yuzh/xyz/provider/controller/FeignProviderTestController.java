package yuzh.xyz.provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Harry
 * @since 2019-07-17 17:11
 */
@RestController
public class FeignProviderTestController {

    @RequestMapping(value = "/feign", method = RequestMethod.GET)
    public String hello() {
        return "hello,feign";
    }

}