package yuzh.xyz.feignuploadserver.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Harry
 * @since 2019-07-17 23:10
 */
@RestController
public class FeignUploadController {

    @PostMapping(value = "/uploadFile/server", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String fileUploadServer(MultipartFile file) throws Exception {
        return file.getOriginalFilename();
    }

}
