package yuzh.xyz.feignuploadserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Harry
 * @since 2019-07-17 23:56
 */
@RestController
public class FeignTokenTransferController {

    @GetMapping("/return/token")
    private String returnToken(HttpServletRequest request) {
        System.out.println(request.getHeader("token"));
        return request.getHeader("token");
    }

}
