package yuzh.xyz.feignuploadclient.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import yuzh.xyz.feignuploadclient.service.FeignTokenTransfer;
import yuzh.xyz.feignuploadclient.service.FileUploadFeignService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Harry
 * @since 2019-07-17 23:23
 */
@RestController
@Api(value = "文件上传")
@RequestMapping("/feign")
public class FeignUploadController {

    @Autowired
    private FileUploadFeignService fileUploadFeignService;
    @Autowired
    private FeignTokenTransfer feignTokenTransfer;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "文件上传", notes = "请选择文件上传")
    public String imageUpload(@ApiParam(value = "文件上传", required = true) MultipartFile file) throws Exception {
        return fileUploadFeignService.fileUpload(file);
    }

    @GetMapping("/token/transfer")
    public String tokenTransfer(HttpServletRequest request) {
        System.out.println("当前 token: " + request.getHeader("token"));
        return "service call - token: " + feignTokenTransfer.returnToken();
    }

}

