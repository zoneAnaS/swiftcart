package com.swiftcart.repository;

import com.swiftcart.dto.ProductResponse;
import com.swiftcart.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {
    public Optional<Category> findByName(String name);
}
