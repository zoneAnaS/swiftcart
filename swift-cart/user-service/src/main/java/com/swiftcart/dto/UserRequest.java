package com.swiftcart.dto;

import com.swiftcart.model.Address;
import com.swiftcart.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
    private String image_url;
    private String password;
    private AddressRequest address;
}
