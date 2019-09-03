package yuzh.xyz.insteadokhttp.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import yuzh.xyz.insteadokhttp.config.HelloFeignConfig;

/**
 * @author Harry
 * @since 2019-07-17 15:39
 */
@FeignClient(name = "github-client", url = "https://api.github.com", configuration = HelloFeignConfig.class)
public interface HelloFeignService {

    /**
     * content: {"message":"Validation Failed","errors":[{"resource":"Search","field":"q","code":"missing"}],
     * "documentation_url":"https://developer.github.com/v3/search"}
     */
    @RequestMapping(value = "/search/repositories", method = RequestMethod.GET)
    String searchRepo(@RequestParam("q") String queryStr);

}
