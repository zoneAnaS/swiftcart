package com.swiftcart.controller;

import com.swiftcart.dto.CategoryRequest;
import com.swiftcart.dto.MessageResponse;
import com.swiftcart.dto.ProductResponse;
import com.swiftcart.exception.CategoryNotFoundException;
import com.swiftcart.model.Category;
import com.swiftcart.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/swiftcart/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{categoryName}/products")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable String categoryName) throws CategoryNotFoundException {
        return new ResponseEntity<>(categoryService.getProductsByCategory(categoryName), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> addCategory(@RequestBody CategoryRequest categoryRequest) throws CategoryNotFoundException {
        return new ResponseEntity<>(new MessageResponse(categoryService.addCategory(categoryRequest)), HttpStatus.CREATED);
    }

    @PutMapping("/{category_id}")
    public ResponseEntity<MessageResponse> updateCategory(@PathVariable String category_id,@RequestBody CategoryRequest categoryRequest) throws CategoryNotFoundException {
        return new ResponseEntity<>(new MessageResponse(categoryService.updateCategory(category_id,categoryRequest)), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<MessageResponse> deleteCategory(String category_id) throws CategoryNotFoundException {
        return new ResponseEntity<>(new MessageResponse(categoryService.deleteCategory(category_id)),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(),HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String categoryId) throws CategoryNotFoundException {
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId),HttpStatus.OK);
    }

    @GetMapping("/name/{categoryName}")
    public ResponseEntity<Category> getCategoryByCategoryName(String categoryName) throws CategoryNotFoundException {
        return new ResponseEntity<>(categoryService.getCategoryByCategoryName(categoryName),HttpStatus.OK);
    }
}
