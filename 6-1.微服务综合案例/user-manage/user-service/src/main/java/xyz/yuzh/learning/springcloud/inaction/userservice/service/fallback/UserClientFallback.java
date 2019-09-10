package xyz.yuzh.learning.springcloud.inaction.userservice.service.fallback;


import org.springframework.stereotype.Component;
import xyz.yuzh.learning.springcloud.inaction.userservice.service.dataservice.DataService;

/**
 * @author Harry
 */
@Component
public class UserClientFallback implements DataService {

    @Override
    public String getDefaultUser() {
        return "get getDefaultUser failed";
    }

    @Override
    public String getContextUserId() {
        return "get getContextUserId failed";
    }

}
