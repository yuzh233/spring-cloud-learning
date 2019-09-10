package xyz.yuzh.learning.springcloud.inaction.userservice.service.dataservice;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xyz.yuzh.learning.springcloud.inaction.userservice.service.fallback.UserClientFallback;


/**
 * feign调用数据服务
 */
@FeignClient(name = "data-service", fallback = UserClientFallback.class)
public interface DataService {

    @RequestMapping(value = "/getDefaultUser", method = RequestMethod.GET)
    String getDefaultUser();

    @RequestMapping(value = "/getContextUserId", method = RequestMethod.GET)
    String getContextUserId();

}
