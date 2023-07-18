package com.sharedJwt.jwt.controller;

import com.sharedJwt.jwt.model.Role;
import com.sharedJwt.jwt.service.JwtService;
import com.sharedJwt.jwt.dto.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/swiftcart/jwt")
public class jwtController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;
    @GetMapping("/{jwtToken}")
    public UserServiceImpl getUserService(@PathVariable String jwtToken){
        UserServiceImpl userService=new UserServiceImpl();
        String username=jwtService.extractUserName(jwtToken);
        UserDetails userDetails=userDetailsService.loadUserByUsername(username);
        userService.setId(userDetails.getUsername());
        userService.setPassword(userDetails.getPassword());
        Collection<? extends GrantedAuthority> authorities=userDetails.getAuthorities();
        String firstAuthority = "user";
        if (!authorities.isEmpty()) {
            GrantedAuthority firstGrantedAuthority = authorities.iterator().next();
            firstAuthority = firstGrantedAuthority.getAuthority();
        }
        userService.setRole(Role.valueOf(firstAuthority));
        return userService;
    }
}
