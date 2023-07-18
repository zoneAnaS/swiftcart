package com.swiftcart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    private String id= UUID.randomUUID().toString().replaceAll("-","");;
    private String name;
    private String description;
    @Column(length = 1000)
    private String image_url;
    private Integer quantity;
    private Double price;
    private LocalDateTime creation_date=LocalDateTime.now();
    @ManyToOne
    private Category category;
}
