package com.swiftcart.service;

import com.swiftcart.dto.*;
import com.swiftcart.exception.CartException;
import com.swiftcart.exception.CartNotFoundException;
import com.swiftcart.model.Cart;
import com.swiftcart.proxy.ProductProxy;
import com.swiftcart.proxy.UserProxy;
import com.swiftcart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private ProductProxy productProxy;
    @Autowired
    private UserProxy userProxy;
    @Autowired
    private CartRepository cartRepository;

    public String updateProduct(String productId,ProductRequest productRequest) throws CartException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println((String)authentication.getCredentials());
        try{
            return productProxy.updateProduct(productId,productRequest).getMessage();
        }catch(Exception exception){
            throw new CartException("Cart not updated");
        }
    }
    public ProductResponse getProductById(String productId) throws CartException {
        ProductResponse productResponse=null;
        try{
            productResponse=productProxy.getProductById(productId);
            if(productResponse==null)throw new CartException("");
        }catch(Exception exception){
            throw new CartException("product not found");
        }
        return productResponse;
    }
    public Cart cartResponseToCart(CartResponse cartResponse){
        Cart cart=new Cart();
        cart.setCartId(cartResponse.getCartId());
        cart.setProductList(cartResponse.getProductList());
        cart.setCartTotal(cartResponse.getCartTotal());
        cart.setUserId(cartResponse.getUserId());
        return cart;
    }
    public ProductRequest productResponseToRequest(ProductResponse productResponse){
        ProductRequest productRequest=new ProductRequest();
        productRequest.setName(productResponse.getName());
        productRequest.setPrice(productResponse.getPrice());
        productRequest.setQuantity(productResponse.getQuantity());
        productRequest.setDescription(productResponse.getDescription());
        productRequest.setImage_url(productResponse.getImage_url());
        productRequest.setCategory(null);
        return productRequest;
    }


    @Override
    public String addItemToCart(String cartId, String productId) throws CartNotFoundException, CartException {
        Cart cart=cartResponseToCart(this.getCartById(cartId));
        System.out.println(cart);
        ProductResponse productResponse=getProductById(productId);
        System.out.println(productResponse);
        if(productResponse.getQuantity()<=0)throw new CartException("Product out of stock");
        ProductRequest productRequest=productResponseToRequest(productResponse);
        productRequest.setQuantity(productRequest.getQuantity()-1);
        String successText=updateProduct(productId,productRequest);
        if(cart.getProductList().containsKey(productId)){
            cart.getProductList().put(productId,cart.getProductList().get(productId)+1);
        }else cart.getProductList().put(productId,1);
        cartRepository.save(cart);
        updateCartTotal(cartId);
        return "Product added to cart";
    }

    @Override
    public String addItemToCart(String cartId, String productId, Integer quantity) throws CartNotFoundException, CartException {
        Cart cart=cartResponseToCart(this.getCartById(cartId));
        ProductResponse productResponse=getProductById(productId);
        if(productResponse.getQuantity()<quantity)throw new CartException("only "+productResponse.getQuantity()+" quantity is available");
        ProductRequest productRequest=productResponseToRequest(productResponse);
        productRequest.setQuantity(productRequest.getQuantity()-quantity);
        String successText=updateProduct(productId,productRequest);
        if(cart.getProductList().containsKey(productId)){
            cart.getProductList().put(productId,cart.getProductList().get(productId)+quantity);
        }else cart.getProductList().put(productId,quantity);
        cartRepository.save(cart);
        updateCartTotal(cartId);
        return "Product added to cart with qty "+quantity;
    }

    @Override
    public String updateCart(String cartId, CartResponse cartResponse) throws CartNotFoundException, CartException {
        CartResponse cartResponse1=getCartById(cartId);
        Map<String,Integer> cartResponseProducts=cartResponse.getProductList();
        Map<String,Integer> cartProducts=cartResponse1.getProductList();
        cartResponse1.setProductList(cartResponse.getProductList()!=null?new HashMap<>() :cartResponse1.getProductList());
        if(cartResponse.getProductList()!=null){
            //updating product quantity from current cart
            for(String productId:cartProducts.keySet()){
                try {
                    ProductRequest productRequest = productResponseToRequest(getProductById(productId));
                    productRequest.setQuantity(productRequest.getQuantity() + cartProducts.get(productId));
                    updateProduct(productId, productRequest);
                }catch(Exception exception){

                }
            }
            //updating product quantity from new cart
            for(String productId:cartResponseProducts.keySet()){
                int count=0;
                try{
                    for(int i=0;i<cartResponseProducts.get(productId);i++){
                        addItemToCart(cartId,productId);
                        cartResponse1.getProductList().put(productId,++count);
                    }

                }catch (Exception exception){

                }
            }
        }

        cartRepository.save(cartResponseToCart(cartResponse1));
        updateCartTotal(cartId);
        return "Cart updated successfully";
    }

    @Override
    public CartResponse getCartById(String cartId) throws CartNotFoundException {
        Cart cart= cartRepository.findById(cartId).orElseThrow(()->new CartNotFoundException("No cart found with id: "+cartId));
//        System.out.println(cart);
        CartResponse cartResponse=new CartResponse(cart.getCartId(),cart.getUserId(),cart.getProductList(), cart.getCartTotal());
        return cartResponse;
    }

    @Override
    public CartResponse getCartByUserId(String userId) throws CartNotFoundException {
        Cart cart= cartRepository.findByUserId(userId).orElseThrow(()->new CartNotFoundException("No cart found with id: "+userId));
//        System.out.println(cart);
        CartResponse cartResponse=new CartResponse(cart.getCartId(),cart.getUserId(),cart.getProductList(), cart.getCartTotal());
        return cartResponse; }


    @Override
    public String addCart(CartRequest cartRequest) throws CartException {
        Optional<Cart> cartResponse=cartRepository.findByUserId(cartRequest.getUserId());
        if(cartResponse.isPresent())throw new CartException("Cart Already present for user");
        try{
            Boolean isUserPresent=userProxy.isUserPresent(cartRequest.getUserId());
            System.out.println(isUserPresent);
            if(isUserPresent==null || !isUserPresent)throw new CartException();
        }catch(Exception exception){
            System.out.println(exception.getMessage());
            throw new CartException("Cannot find the user");
        }
        Cart cart=new Cart();
        cart.setUserId(cartRequest.getUserId());
        cartRepository.save(cart);

        return "Cart added successfully";
    }

    @Override
    public String deleteProductFromCart(String cartId, String productId) throws CartNotFoundException, CartException {
        Cart cart=cartResponseToCart(getCartById(cartId));
        Map<String,Integer> products=cart.getProductList();
        if(!products.containsKey(productId))throw new CartException("Product is not is the cart");
        try{
            ProductResponse productResponse=getProductById(productId);
            productResponse.setQuantity(productResponse.getQuantity()+1);
            updateProduct(productId,productResponseToRequest(productResponse));
        }catch (Exception exception){
            throw new CartException("Product not removed");
        }

        if(products.get(productId)==1)products.remove(productId);
        else products.put(productId,products.get(productId)-1);
        cart.setProductList(products);
        cartRepository.save(cart);
        updateCartTotal(cartId);
        return "Product removed from the cart";
    }

    @Override
    public String updateCartTotal(String cartId) throws CartNotFoundException, CartException {
        Cart cart = cartResponseToCart(getCartById(cartId));
        Double total=0.0;
        for(String productId:cart.getProductList().keySet()){
            try {
                ProductResponse productResponse=getProductById(productId);
                total+=productResponse.getPrice()*cart.getProductList().get(productId);
            }catch(Exception exception){

            }

        }
        cart.setCartTotal(total);
        cartRepository.save(cart);
        return "cart total is "+total;
    }

    @Override
    public String emptyCart(String userId) throws CartNotFoundException, CartException {
        CartResponse cartResponse=this.getCartByUserId(userId);
        Cart cart=cartRepository.findByUserId(userId).get();
        cart.setProductList(new HashMap<>());
        cartRepository.save(cart);
        this.updateCartTotal(cart.getCartId());
        return "Cart emptied successfully";
    }
}
