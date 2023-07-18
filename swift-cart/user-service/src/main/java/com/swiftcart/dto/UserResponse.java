package com.swiftcart.dto;

import com.swiftcart.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
    private String image_url;
    private AddressRequest address;
}
