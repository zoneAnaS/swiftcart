package com.swiftcart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {
    @Id
    private String cartId= UUID.randomUUID().toString().replaceAll("-","");
    private String userId;
    @ElementCollection
    private Map<String,Integer> productList=new HashMap<>();
    private Double cartTotal=0.0;
}
