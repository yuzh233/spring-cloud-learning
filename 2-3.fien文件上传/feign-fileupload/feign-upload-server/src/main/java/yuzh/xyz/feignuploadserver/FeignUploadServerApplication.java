package yuzh.xyz.feignuploadserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FeignUploadServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignUploadServerApplication.class, args);
    }

}
