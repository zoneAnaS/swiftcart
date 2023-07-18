package com.swiftcart.dto;

import com.swiftcart.model.OrderedProduct;
import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderBillResponse {
    private LocalDateTime billDate=LocalDateTime.now();
    private Double discount;
    private Double deliveryCharge;
    private List<OrderedProduct> orderedProductList;
    private Double billTotal;
}
