package xyz.yuzh.learning.springcloud.hystrix.providerservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Harry Zhang
 * @since 2019-09-02 21:58
 */
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
