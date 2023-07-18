package com.sharedJwt.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SharedJwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(SharedJwtApplication.class,args);
    }
}
