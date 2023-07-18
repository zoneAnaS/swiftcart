package com.swiftcart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    private String orderId;
    private String userId;
    private LocalDateTime orderDate=LocalDateTime.now();
    @ElementCollection
    private Map<String,Integer> productList=new HashMap<>();
    private Double orderTotal=0.0;
    private Status status;
    @Embedded
    @JsonIgnore
    private OrderBill orderBill;
}
