package com.swiftcart.controller;

import com.swiftcart.dto.MessageResponse;
import com.swiftcart.dto.ProductRequest;
import com.swiftcart.dto.ProductResponse;
import com.swiftcart.exception.CategoryNotFoundException;
import com.swiftcart.exception.ProductNotFoundException;
import com.swiftcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/swiftcart/products")
@CrossOrigin(originPatterns = "**")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String product_id) throws ProductNotFoundException {
        return new ResponseEntity<>(productService.getProductById(product_id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> addProduct(@RequestBody ProductRequest productRequest) throws CategoryNotFoundException {
        return new ResponseEntity<>(new MessageResponse(productService.addProduct(productRequest)),HttpStatus.CREATED);
    }

    @PostMapping("/all")
    public ResponseEntity<MessageResponse> addAllProduct(@RequestBody List<ProductRequest> productRequests) {
        return new ResponseEntity<>(new MessageResponse(productService.addAllProduct(productRequests)),HttpStatus.CREATED);
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<MessageResponse> updateProduct(@PathVariable String product_id,@RequestBody ProductRequest product) throws ProductNotFoundException {
        return new ResponseEntity<>(new MessageResponse(productService.updateProduct(product_id,product)),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable String product_id) throws ProductNotFoundException {
        return new ResponseEntity<>(new MessageResponse(productService.deleteProduct(product_id)),HttpStatus.OK);
    }
}
