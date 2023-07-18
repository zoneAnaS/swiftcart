package com.swiftcart.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserProxy {

    @GetMapping("/swiftcart/users/check/{userId}")
    public Boolean isUserPresent(@PathVariable String userId);
}
