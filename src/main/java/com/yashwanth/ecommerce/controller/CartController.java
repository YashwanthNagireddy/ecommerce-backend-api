package com.yashwanth.ecommerce.controller;


import com.yashwanth.ecommerce.entity.Cart;
import com.yashwanth.ecommerce.service.CartService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/cart")
public class CartController {


    private final CartService cartService;



    public CartController(
            CartService cartService
    ) {

        this.cartService = cartService;

    }




    @PostMapping("/{productId}")
    public Cart addToCart(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            Authentication authentication
    ) {


        String email = authentication.getName();


        return cartService.addToCart(
                productId,
                quantity,
                email
        );

    }




    @GetMapping
    public List<Cart> getCart(
            Authentication authentication
    ) {


        String email = authentication.getName();


        return cartService.getCartItems(email);

    }





    @DeleteMapping("/{cartId}")
    public String removeCartItem(
            @PathVariable Long cartId
    ) {


        return cartService.removeCartItem(cartId);

    }

}