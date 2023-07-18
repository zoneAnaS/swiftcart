package com.swiftcart.dto;

import com.swiftcart.model.Status;
import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String orderId;
    private String userId;
    private LocalDateTime orderDate=LocalDateTime.now();
    private Map<String,Integer> productList=new HashMap<>();
    private Double orderTotal=0.0;
    private Status status;
}
