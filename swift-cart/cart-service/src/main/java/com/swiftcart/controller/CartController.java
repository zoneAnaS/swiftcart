package com.swiftcart.controller;

import com.swiftcart.dto.CartRequest;
import com.swiftcart.dto.CartResponse;
import com.swiftcart.dto.MessageResponse;
import com.swiftcart.dto.ProductResponse;
import com.swiftcart.exception.CartException;
import com.swiftcart.exception.CartNotFoundException;
import com.swiftcart.proxy.ProductProxy;
import com.swiftcart.service.CartService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/swiftcart/carts")
public class CartController{
    @Autowired
    private CartService cartService;




    @PostMapping("/{cartId}/addToCart/{productId}")
    public ResponseEntity<MessageResponse> addItemToCart(@PathVariable String cartId, @PathVariable String productId) throws CartNotFoundException, CartException {
        return new ResponseEntity<>(new MessageResponse(cartService.addItemToCart(cartId,productId)), HttpStatus.ACCEPTED);
    }
    @PostMapping("/{cartId}/addToCart/{productId}/qty/{quantity}")
    public ResponseEntity<MessageResponse> addItemToCart(@PathVariable String cartId, @PathVariable String productId,@PathVariable Integer quantity) throws CartNotFoundException, CartException {
        return new ResponseEntity<>(new MessageResponse(cartService.addItemToCart(cartId,productId,quantity)), HttpStatus.ACCEPTED);
    }


    @PutMapping("/{cartId}")
    public ResponseEntity<MessageResponse> updateCart(@PathVariable String cartId,@RequestBody CartResponse cartResponse) throws CartNotFoundException, CartException {
        return new ResponseEntity<>(new MessageResponse(cartService.updateCart(cartId,cartResponse)), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable String cartId) throws CartNotFoundException {
        return new ResponseEntity<>(cartService.getCartById(cartId),HttpStatus.OK);
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<MessageResponse> addCart(@PathVariable String userId) throws CartException {
        System.out.println("Adding cart");
        return new ResponseEntity<>(new MessageResponse(cartService.addCart(new CartRequest(userId))),HttpStatus.CREATED);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponse> getCartByUserId(@PathVariable String userId) throws CartNotFoundException {
        return new ResponseEntity<>(cartService.getCartByUserId(userId),HttpStatus.OK);
    }


    @DeleteMapping("/{cartId}/removeFromCart/{productId}")
    public ResponseEntity<MessageResponse> deleteProductFromCart(@PathVariable String cartId,@PathVariable String productId) throws CartNotFoundException, CartException {
        return new ResponseEntity<>(new MessageResponse(cartService.deleteProductFromCart(cartId,productId)),HttpStatus.OK);
    }

    @PostMapping("/{cartId}/total")
    public ResponseEntity<MessageResponse> updateCartTotal(String cartId) throws CartNotFoundException, CartException {
        return new ResponseEntity<>(new MessageResponse(cartService.updateCartTotal(cartId)),HttpStatus.OK);

    }

    @PostMapping("/user/{userId}/empty")
    public ResponseEntity<MessageResponse> emptyCart(@PathVariable String userId) throws CartNotFoundException, CartException {
        return new ResponseEntity<>(new MessageResponse(cartService.emptyCart(userId)),HttpStatus.ACCEPTED);
    }
}
