package com.swiftcart.dto;

import com.swiftcart.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseConfig {
    private String id;
    private Role role;
    private String password;
}
