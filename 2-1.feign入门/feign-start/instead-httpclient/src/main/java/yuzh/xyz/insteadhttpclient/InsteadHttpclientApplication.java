package yuzh.xyz.insteadhttpclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InsteadHttpclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsteadHttpclientApplication.class, args);
    }

}
