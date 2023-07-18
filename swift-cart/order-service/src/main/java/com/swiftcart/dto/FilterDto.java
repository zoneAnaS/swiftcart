package com.swiftcart.dto;

import com.swiftcart.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterDto {
    private LocalDate orderDate;
    private Integer productCount;
    private Status OrderStatus;
}
