package com.sharedJwt.jwt.dto;

import com.sharedJwt.jwt.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl {
    private String id;
    private Role role;
    private String password;
}
