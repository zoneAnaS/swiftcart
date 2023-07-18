package com.swiftcart.service;

import com.swiftcart.dto.AddressRequest;
import com.swiftcart.dto.UserRequest;
import com.swiftcart.dto.UserResponse;
import com.swiftcart.dto.UserResponseConfig;
import com.swiftcart.exception.UserNotFoundException;
import com.swiftcart.model.Address;
import com.swiftcart.model.Role;
import com.swiftcart.model.User;
import com.swiftcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    private UserResponse userToUserResponse(User user){
        UserResponse userResponse=UserResponse.builder()
                .id(user.getId())
                .role(user.getRole())
                .email(user.getEmail())
                .image_url(user.getImage_url())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build();
        Address address=user.getAddress();
        AddressRequest addressRequest=AddressRequest.builder()
                .city(address.getCity())
                .pin(address.getPin())
                .state(address.getState())
                .country(address.getCountry())
                .street(address.getStreet())
                .build();
        userResponse.setAddress(addressRequest);
        return userResponse;
    }
    @Override
    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream().map(this::userToUserResponse).toList();
    }

    @Override
    public UserResponse getUserById(String userId) throws UserNotFoundException {
        User user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("No user found with id: "+userId));
        return this.userToUserResponse(user);
    }

    @Override
    public String updateUser(String userId,UserRequest userRequest) throws UserNotFoundException {
//        System.out.println(userRequest);
        User user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("No user found with id: "+userId));
        if(userRequest.getLastname()!=null)user.setLastname(userRequest.getLastname());
        if(userRequest.getFirstname()!=null)user.setFirstname(userRequest.getFirstname());
        if(userRequest.getImage_url()!=null)user.setImage_url(userRequest.getImage_url());
        Address address=user.getAddress();
        AddressRequest addressRequest=userRequest.getAddress();
        if(addressRequest.getCity()!=null)address.setCity(addressRequest.getCity());
        if(addressRequest.getPin()!=null)address.setPin(addressRequest.getPin());
        if(addressRequest.getStreet()!=null)address.setStreet(addressRequest.getStreet());
        if(addressRequest.getState()!=null)address.setState(addressRequest.getState());
        if(addressRequest.getCountry()!=null)address.setCountry(addressRequest.getCountry());
        user.setAddress(address);
        System.out.println(user);
        userRepository.save(user);
        return "User updated successfully";
    }

    @Override
    public UserResponseConfig getConfigUser(String userId) throws UserNotFoundException {
        User user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("No user found with id: "+userId));

        return new UserResponseConfig(user.getId(), user.getRole(),user.getPassword());
    }
}
