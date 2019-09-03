package xyz.yuzh.learning.springcloud.hystrix.helloservice.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 调用 provider-service 服务的接口失败 —— 降级回调处理方法
 *
 * @author Harry Zhang
 * @since 2019-09-02 21:25
 */
@Component
public class ProviderServiceFallback implements ProviderService {

    @Override
    public List<String> getProviderData() {
        List<String> list = new ArrayList<>();
        list.add("no result!");
        return list;
    }

}
