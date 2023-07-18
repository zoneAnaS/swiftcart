package com.swiftcart.service;

import com.swiftcart.dto.CartRequest;
import com.swiftcart.dto.CartResponse;
import com.swiftcart.exception.CartException;
import com.swiftcart.exception.CartNotFoundException;

public interface CartService {
    public String addItemToCart(String cartId,String productId) throws CartNotFoundException, CartException;
    public String addItemToCart(String cartId,String productId,Integer quantity) throws CartNotFoundException, CartException;
    public String updateCart(String cartId,CartResponse cartResponse) throws CartNotFoundException, CartException;
    public CartResponse getCartById(String cartId) throws CartNotFoundException;
    public CartResponse getCartByUserId(String cartId) throws CartNotFoundException;
    public String addCart(CartRequest cartRequest) throws CartException;
    public String deleteProductFromCart(String cartId,String productId) throws CartNotFoundException, CartException;
    public String updateCartTotal(String cartId) throws CartNotFoundException, CartException;
    public String emptyCart(String userId)throws CartNotFoundException,CartException;
}
