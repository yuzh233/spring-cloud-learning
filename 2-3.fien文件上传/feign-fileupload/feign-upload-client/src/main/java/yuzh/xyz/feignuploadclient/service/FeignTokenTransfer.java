package yuzh.xyz.feignuploadclient.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Harry
 * @since 2019-07-17 23:49
 */
@FeignClient(value = "feign-upload-server")
public interface FeignTokenTransfer {

    @GetMapping("/return/token")
    String returnToken();

}
