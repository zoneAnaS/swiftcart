package com.swiftcart.service;

import com.swiftcart.dto.AddressRequest;
import com.swiftcart.dto.CartRequest;
import com.swiftcart.dto.LoginRequest;
import com.swiftcart.dto.UserRequest;
import com.swiftcart.exception.UserException;
import com.swiftcart.exception.UserNotFoundException;
import com.swiftcart.model.Address;
import com.swiftcart.model.Role;
import com.swiftcart.model.User;
import com.swiftcart.model.UserPassword;
import com.swiftcart.proxy.CartProxy;
import com.swiftcart.repository.UserPasswordRepository;
import com.swiftcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class NoAuthServiceImpl implements AuthService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartProxy cartProxy;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Override
    public String addUser(UserRequest userRequest) throws UserException {
        System.out.println(userRequest);
        Optional<User> userOptional=userRepository.findByEmail(userRequest.getEmail());
        if(userOptional.isPresent())throw new UserException("email already registered!");
        User user = new User();
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setId(UUID.randomUUID().toString().replaceAll("-",""));
        user.setImage_url(userRequest.getImage_url());
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());
        AddressRequest addressRequest = userRequest.getAddress();
        Address address=new Address();
        address.setCountry(addressRequest.getCountry());
        address.setCity(addressRequest.getCity());
        address.setStreet(addressRequest.getStreet());
        address.setPin(addressRequest.getPin());
        address.setState(addressRequest.getState());
        user.setAddress(address);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User user1=userRepository.save(user);
        System.out.println("User:  "+user);
        System.out.println("User1: []"+user1);

        try{
            System.out.println("adding cart from user service");
            cartProxy.addCart(user.getId());
        }catch(Exception exception){
            System.out.println(exception.getMessage());
            userRepository.delete(user);
            throw new UserException("Something went wrong! try again");
        }
//        userPasswordRepository.save(new UserPassword(user.getId(), passwordEncoder.encode(userRequest.getPassword())));
        return "User added successfully";
    }

    @Override
    public String login(LoginRequest loginRequest) throws Exception {
        User user=userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new UserNotFoundException("No user found with email ["+loginRequest.getEmail()+"]"));
        String password=user.getPassword();
        if(passwordEncoder.matches(loginRequest.getPassword(),password)){
             return jwtService.generateToken(user);
        }else throw new UserException("Invalid password");
    }
}
