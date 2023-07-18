package com.swiftcart.proxy;

import com.swiftcart.dto.CartResponse;
import com.swiftcart.dto.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("cart-service")
public interface CartProxy {
    @GetMapping("/swiftcart/carts/user/{userId}")
    public CartResponse getCartByUserId(@PathVariable String userId) ;
    @PostMapping("/swiftcart/carts/user/{userId}/empty")
    public MessageResponse emptyCart(@PathVariable String userId);
}
