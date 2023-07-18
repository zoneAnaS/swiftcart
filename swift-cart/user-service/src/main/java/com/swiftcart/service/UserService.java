package com.swiftcart.service;

import com.swiftcart.dto.UserRequest;
import com.swiftcart.dto.UserResponse;
import com.swiftcart.dto.UserResponseConfig;
import com.swiftcart.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    public List<UserResponse> getAllUser();
    public UserResponse getUserById(String userId) throws UserNotFoundException;
    public String updateUser(String userId,UserRequest userRequest) throws UserNotFoundException;
    public UserResponseConfig getConfigUser(String userId) throws UserNotFoundException;
}
