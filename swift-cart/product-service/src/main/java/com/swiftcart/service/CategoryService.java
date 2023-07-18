package com.swiftcart.service;

import com.swiftcart.dto.CategoryRequest;
import com.swiftcart.dto.ProductResponse;
import com.swiftcart.exception.CategoryNotFoundException;
import com.swiftcart.model.Category;

import java.util.List;

public interface CategoryService {

    public List<ProductResponse> getProductsByCategory(String categoryName) throws CategoryNotFoundException;
    public String addCategory(CategoryRequest categoryRequest) throws CategoryNotFoundException;
    public String updateCategory(String category_id,CategoryRequest categoryRequest) throws CategoryNotFoundException;
    public String deleteCategory(String category_id) throws CategoryNotFoundException;
    public List<Category> getAllCategories();
    public Category getCategoryById(String categoryId) throws CategoryNotFoundException;
    public Category getCategoryByCategoryName(String categoryName) throws CategoryNotFoundException;
}
