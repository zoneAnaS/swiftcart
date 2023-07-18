package com.swiftcart.service;

import com.swiftcart.dto.CategoryRequest;
import com.swiftcart.dto.ProductResponse;
import com.swiftcart.exception.CategoryNotFoundException;
import com.swiftcart.model.Category;
import com.swiftcart.model.Product;
import com.swiftcart.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    private ProductResponse ProductToProductResponse(Product product){
        return new ProductResponse(product.getId(),
                product.getName(), product.getDescription(),
                product.getImage_url(),
                product.getQuantity(),
                product.getCategory(),product.getPrice());
    }
    @Override
    public List<ProductResponse> getProductsByCategory(String categoryName) throws CategoryNotFoundException {
        Category category=this.getCategoryByCategoryName(categoryName);
        return category.getProductList().stream().map(this::ProductToProductResponse).toList();
    }

    @Override
    public String addCategory(CategoryRequest categoryRequest) throws CategoryNotFoundException {
        Optional<Category> category1 = categoryRepository.findByName(categoryRequest.getName());
        if(category1.isPresent())throw new CategoryNotFoundException("Category with name already present");
        Category category=new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setImage_url(categoryRequest.getImage_url());
        category.setProductList(new ArrayList<>());
        categoryRepository.save(category);
        return "Category added successfully";
    }

    @Override
    public String updateCategory(String category_id, CategoryRequest categoryRequest) throws CategoryNotFoundException {
        Category category=getCategoryById(category_id);
        if(categoryRequest.getName()!=null)category.setName(categoryRequest.getName());
        if(categoryRequest.getDescription()!=null)category.setDescription(categoryRequest.getDescription());
        if(categoryRequest.getImage_url()!=null)category.setImage_url(categoryRequest.getImage_url());
        categoryRepository.save(category);
        return "Category updated successfully";
    }

    @Override
    public String deleteCategory(String category_id) throws CategoryNotFoundException {
        Category category=this.getCategoryById(category_id);
        categoryRepository.delete(category);
        return "Category deleted successfully";
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByCategoryName(String categoryName) throws CategoryNotFoundException {
        return categoryRepository.findByName(categoryName).orElseThrow(()->new CategoryNotFoundException("No category found with name "+categoryName));

    }

    @Override
    public Category getCategoryById(String categoryId) throws CategoryNotFoundException {
        return categoryRepository.findById(categoryId).orElseThrow(()->new CategoryNotFoundException("No category found with id "+categoryId));
    }
}
