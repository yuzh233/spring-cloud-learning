package xyz.yuzh.learning.springcloud.inaction.userservice.service.dataservice;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.yuzh.learning.springcloud.inaction.userservice.service.fallback.UserClientFallback;


/**
 * feign 调用数据服务
 *
 * @author Harry
 */
@FeignClient(name = "data-service", fallback = UserClientFallback.class)
public interface DataService {

    @GetMapping(value = "/getDefaultUser")
    String getDefaultUser();

    @GetMapping(value = "/getContextUserId")
    String getContextUserId();

}
