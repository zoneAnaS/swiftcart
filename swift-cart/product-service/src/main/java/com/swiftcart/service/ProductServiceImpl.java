package com.swiftcart.service;

import com.swiftcart.dto.CategoryRequest;
import com.swiftcart.dto.ProductRequest;
import com.swiftcart.dto.ProductResponse;
import com.swiftcart.exception.CategoryNotFoundException;
import com.swiftcart.exception.ProductNotFoundException;
import com.swiftcart.model.Category;
import com.swiftcart.model.Product;
import com.swiftcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAllResponse();
    }

    @Override
    public ProductResponse getProductById(String product_id) throws ProductNotFoundException {
        return productRepository.findResponseById(product_id)
                .orElseThrow(
                        ()->new ProductNotFoundException("No product with id "+product_id+" found")
                );
    }


    public Product getProductByIdAcc(String product_id) throws ProductNotFoundException {
        return productRepository.findById(product_id)
                .orElseThrow(
                        ()->new ProductNotFoundException("No product with id "+product_id+" found")
                );
    }

    @Override
    public String addProduct(ProductRequest productRequest) throws CategoryNotFoundException {
        Product product=Product.builder()
                .id(UUID.randomUUID().toString().replaceAll("-",""))
                .creation_date(LocalDateTime.now())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .image_url(productRequest.getImage_url())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();
        Category category=null;
        try{
            category=categoryService.getCategoryByCategoryName(productRequest.getCategory().getName());
        }catch (Exception ex){
            CategoryRequest categoryRequest=productRequest.getCategory();
            categoryService.addCategory(categoryRequest);
            category=categoryService.getCategoryByCategoryName(productRequest.getCategory().getName());
        }finally {
            product.setCategory(category);
            productRepository.save(product);
        }


        return "Product added successfully";
    }

    @Override
    public String addAllProduct(List<ProductRequest> productRequests) {
        productRequests.forEach(p->{
            try{
                this.addProduct(p);
            }catch (CategoryNotFoundException ex){

            }
        });
        return "All added successfully";
    }

    @Override
    public String updateProduct(String product_id, ProductRequest productRequest) throws ProductNotFoundException {
        Product product1=this.getProductByIdAcc(product_id);
        Category category=product1.getCategory();
        if(productRequest.getCategory()!=null){
            try {
                Category category1 = categoryService.getCategoryByCategoryName(productRequest.getCategory().getName());
                category=category1;
            }catch (Exception exception){

            }
        }
        Product product=Product.builder()
                .name(productRequest.getName()!=null?productRequest.getName():product1.getName())
                .id(product_id)
                .description(productRequest.getDescription()!=null?productRequest.getDescription():product1.getDescription())
                .category(category)
                .quantity(productRequest.getQuantity()!=null?productRequest.getQuantity():product1.getQuantity())
                .image_url(productRequest.getImage_url()!=null?productRequest.getImage_url():product1.getImage_url())
                .creation_date(product1.getCreation_date())
                .price(productRequest.getPrice()!=null?productRequest.getPrice():product1.getPrice())
                .build();
        productRepository.save(product);
        return "Product updated successfully!";
    }

    @Override
    public String deleteProduct(String product_id) throws ProductNotFoundException {
        this.getProductById(product_id);
        productRepository.deleteById(product_id);
        return "Product with id "+product_id+" deleted Successfully";
    }
}
