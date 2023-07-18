package com.swiftcart.controller;

import com.swiftcart.dto.*;
import com.swiftcart.exception.UserException;
import com.swiftcart.model.User;
import com.swiftcart.repository.UserRepository;
import com.swiftcart.service.AuthService;
import com.swiftcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/swiftcart")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;



    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        String token= authService.login(loginRequest);
        User user=userRepository.findByEmail(loginRequest.getEmail()).get();
        return new ResponseEntity<>(new TokenResponse(token,user.getId(),user.getRole()),HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody UserRequest userRequest) throws UserException {

        return new ResponseEntity<>(new MessageResponse(authService.addUser(userRequest)),HttpStatus.CREATED);
    }


}
