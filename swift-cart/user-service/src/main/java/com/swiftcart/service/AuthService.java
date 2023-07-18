package com.swiftcart.service;

import com.swiftcart.dto.LoginRequest;
import com.swiftcart.dto.UserRequest;
import com.swiftcart.exception.UserException;
import com.swiftcart.model.User;
import reactor.core.publisher.Mono;

public interface AuthService {
    public String addUser(UserRequest userRequest) throws UserException;
    public String login(LoginRequest loginRequest) throws Exception;
}
