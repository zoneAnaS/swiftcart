package com.swiftcart.repository;

import com.swiftcart.dto.CartResponse;
import com.swiftcart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,String> {

    public Optional<Cart> findByUserId(String userId);
}
