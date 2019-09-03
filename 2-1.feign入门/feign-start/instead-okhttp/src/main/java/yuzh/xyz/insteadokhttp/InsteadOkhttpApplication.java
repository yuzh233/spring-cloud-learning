package yuzh.xyz.insteadokhttp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InsteadOkhttpApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsteadOkhttpApplication.class, args);
    }

}
