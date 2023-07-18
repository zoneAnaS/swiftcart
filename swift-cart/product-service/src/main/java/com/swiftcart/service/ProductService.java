package com.swiftcart.service;


import com.swiftcart.dto.ProductRequest;
import com.swiftcart.dto.ProductResponse;
import com.swiftcart.exception.CategoryNotFoundException;
import com.swiftcart.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {

    public List<ProductResponse> getAllProducts();
    public ProductResponse getProductById(String product_id) throws ProductNotFoundException;
    public String addProduct(ProductRequest productRequest) throws CategoryNotFoundException;
    public String addAllProduct(List<ProductRequest> productRequests);
    public String updateProduct(String product_id,ProductRequest product) throws ProductNotFoundException;
    public String deleteProduct(String product_id) throws ProductNotFoundException;

}
