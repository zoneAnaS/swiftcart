package com.swiftcart.repository;

import com.swiftcart.dto.ProductResponse;
import com.swiftcart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

    @Query("SELECT new com.swiftcart.dto.ProductResponse(p.id,p.name,p.description,p.image_url,p.quantity,p.category,p.price) FROM Product p")
    public List<ProductResponse> findAllResponse();

    @Query("SELECT new com.swiftcart.dto.ProductResponse(p.id,p.name,p.description,p.image_url,p.quantity,p.category,p.price) FROM Product p WHERE p.id=?1")
    public Optional<ProductResponse> findResponseById(String productId);
}
