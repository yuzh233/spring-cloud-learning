package yuzh.xyz.feignuploadclient.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import yuzh.xyz.feignuploadclient.config.FeignMultipartSupportConfig;

/**
 * @author Harry
 * @since 2019-07-17 23:14
 */
@FeignClient(value = "feign-upload-server", configuration = FeignMultipartSupportConfig.class)
public interface FileUploadFeignService {

    /***
     * 1. produces, consumes 必填
     * 2. 注意区分 @RequestPart 和 RequestParam，不要将 @RequestPart(value = "file") 写成 @RequestParam(value = "file")
     */
    @RequestMapping(method = RequestMethod.POST, value = "/uploadFile/server",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String fileUpload(@RequestPart(value = "file") MultipartFile file);

}
