package com.swiftcart.proxy;

import com.swiftcart.dto.CartRequest;
import com.swiftcart.dto.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("cart-service")
public interface CartProxy {
    @PostMapping("/swiftcart/carts/add/{userId}")
    public MessageResponse addCart(@PathVariable String userId);
}
