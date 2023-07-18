package com.swiftcart.controller;

import com.swiftcart.dto.MessageResponse;
import com.swiftcart.dto.UserRequest;
import com.swiftcart.dto.UserResponse;
import com.swiftcart.dto.UserResponseConfig;
import com.swiftcart.exception.UserNotFoundException;
import com.swiftcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/swiftcart/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/check/{userId}")
    public ResponseEntity<Boolean> isUserPresent(@PathVariable String userId) throws UserNotFoundException {
        System.out.println("this is is user present");
        Boolean userPresent=userService.getUserById(userId).getId().equals(userId);
        System.out.println(userPresent);
        return new ResponseEntity<>(userPresent,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String userId) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }
    @GetMapping("/check/get/{userId}")
    public ResponseEntity<UserResponseConfig> getUserByIdId(@PathVariable String userId) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getConfigUser(userId),HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<MessageResponse> updateUser(@PathVariable String userId, @RequestBody UserRequest userRequest) throws UserNotFoundException {
        return new ResponseEntity<>(new MessageResponse(userService.updateUser(userId,userRequest)),HttpStatus.ACCEPTED);
    }
}
