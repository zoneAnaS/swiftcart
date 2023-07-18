package com.swiftcart.service;

import com.swiftcart.dto.LoginRequest;
import com.swiftcart.dto.TokenResponse;
import com.swiftcart.dto.UserRequest;
import com.swiftcart.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

//@Service
public class AuthServiceImpl implements AuthService{
    @Override
    public String addUser(UserRequest userRequest) {
        return "User Added successfully";
    }


    @Override
    public String login(LoginRequest loginRequest) throws Exception {
        RestTemplate restTemplate=new RestTemplate();
        String url = "http://localhost:8080/realms/swift-cart/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", "spring-swift-cart");
        formData.add("client_secret", "HtAYaYhoqoBK5XB21Ui6u0csvnZQhP1c");
        formData.add("username", loginRequest.getEmail());
        formData.add("password", loginRequest.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
        try{
            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(url, request, TokenResponse.class);
            TokenResponse tokenResponse = response.getBody();
            if (tokenResponse != null) {
                // Handle the token response
                return tokenResponse.getAccess_token();
            } else {
                throw new IllegalStateException("Failed to retrieve token");
            }
        }catch (IllegalStateException exception){
            throw new IllegalStateException("Failed to retrieve token");
        }
        catch (Exception exception){
            throw new Exception("Invalid Credentials");
        }



    }
}
