package com.sharedJwt.jwt.proxy;


import com.sharedJwt.jwt.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserJwtProxy {
    @GetMapping("/swiftcart/users/check/get/{userId}")
    public User getUserByIdId(@PathVariable String userId);
}
