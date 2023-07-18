package com.swiftcart.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl {
    private String id;
    private Role role;
    private String password;
}
