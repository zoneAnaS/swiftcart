package com.swiftcart.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    private String id= UUID.randomUUID().toString().replaceAll("-","");
    private String name;
    private String description;
    private String image_url;

}
