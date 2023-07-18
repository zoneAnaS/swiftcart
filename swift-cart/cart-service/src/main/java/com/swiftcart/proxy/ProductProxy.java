package com.swiftcart.proxy;

import com.swiftcart.config.JwtTokenInterceptor;
import com.swiftcart.dto.MessageResponse;
import com.swiftcart.dto.ProductRequest;
import com.swiftcart.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;


@FeignClient("product-service")
public interface ProductProxy {

    @GetMapping("/swiftcart/products/{productId}")
    public ProductResponse getProductById(@PathVariable String productId);

    @PutMapping ("/swiftcart/products/{productId}")
    public MessageResponse updateProduct(@PathVariable String productId, @RequestBody ProductRequest productRequest);
}
