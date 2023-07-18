package com.swiftcart.proxy;


import com.swiftcart.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("product-service")
public interface ProductProxy {
    @GetMapping("/swiftcart/products/{productId}")
    public ProductResponse getProductById(@PathVariable String productId);

}
