package com.swiftcart.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderBill {
    private LocalDateTime billDate=LocalDateTime.now();
    private Double discount;
    private Double deliveryCharge;
    @ElementCollection
    private List<OrderedProduct> orderedProductList=new ArrayList<>();
    private Double billTotal;
}
