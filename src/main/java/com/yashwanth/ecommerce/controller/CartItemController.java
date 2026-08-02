package com.yashwanth.ecommerce.controller;

import com.yashwanth.ecommerce.entity.CartItem;
import com.yashwanth.ecommerce.repository.CartItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    private final CartItemRepository cartItemRepository;

    public CartItemController(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @PostMapping
    public CartItem addCartItem(@RequestBody CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @GetMapping
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        cartItemRepository.deleteById(id);
        return "Cart item deleted successfully";
    }
}