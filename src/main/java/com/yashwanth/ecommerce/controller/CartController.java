package com.yashwanth.ecommerce.controller;

import com.yashwanth.ecommerce.entity.Cart;
import com.yashwanth.ecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ADD PRODUCT TO CART
    @PostMapping("/{productId}")
    public ResponseEntity<Cart> addToCart(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                cartService.addToCart(
                        productId,
                        quantity,
                        email
                )
        );
    }

    // GET MY CART
    @GetMapping
    public ResponseEntity<List<Cart>> getCart(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                cartService.getCartItems(email)
        );
    }

    // REMOVE CART ITEM
    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> removeCartItem(
            @PathVariable Long cartId,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                cartService.removeCartItem(
                        cartId,
                        email
                )
        );
    }

    // CLEAR CART
    @DeleteMapping
    public ResponseEntity<String> clearCart(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                cartService.clearCart(email)
        );
    }
}