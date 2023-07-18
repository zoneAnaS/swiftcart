package com.swiftcart.config;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("jwt-shared-service")
public interface JwtProxy {
    @GetMapping("/swiftcart/jwt/{jwtToken}")
    public UserServiceImpl getUserService(@PathVariable String jwtToken);
}
