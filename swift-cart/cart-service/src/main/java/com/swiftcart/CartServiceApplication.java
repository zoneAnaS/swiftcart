package com.swiftcart;

import com.swiftcart.config.FeignClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients
public class CartServiceApplication {
    public static void main(String[] args) {

        SpringApplication.run(CartServiceApplication.class,args);
    }
}
