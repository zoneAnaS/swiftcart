package com.swiftcart.dto;

import com.swiftcart.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    private String name;
    private String description;
    private String image_url;
    private Integer quantity;
    private CategoryRequest category;
    private Double price;
}
