package com.yashwanth.ecommerce.service;

import com.yashwanth.ecommerce.entity.Cart;
import com.yashwanth.ecommerce.entity.Product;
import com.yashwanth.ecommerce.entity.User;
import com.yashwanth.ecommerce.repository.CartRepository;
import com.yashwanth.ecommerce.repository.ProductRepository;
import com.yashwanth.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(
            CartRepository cartRepository,
            ProductRepository productRepository,
            UserRepository userRepository
    ) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // ADD PRODUCT TO CART
    public Cart addToCart(
            Long productId,
            int quantity,
            String email
    ) {

        if (quantity <= 0) {
            throw new RuntimeException(
                    "Quantity must be greater than zero"
            );
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found")
                );

        List<Cart> existingItems = cartRepository.findByUser(user);

        for (Cart cart : existingItems) {

            if (cart.getProduct() != null
                    && cart.getProduct().getId().equals(productId)) {

                cart.setQuantity(
                        cart.getQuantity() + quantity
                );

                return cartRepository.save(cart);
            }
        }

        Cart cart = new Cart();

        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(quantity);

        return cartRepository.save(cart);
    }

    // GET CURRENT USER CART
    public List<Cart> getCartItems(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return cartRepository.findByUser(user);
    }

    // REMOVE CART ITEM
    public String removeCartItem(
            Long cartId,
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() ->
                        new RuntimeException("Cart item not found")
                );

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new RuntimeException(
                    "You are not authorized to remove this cart item"
            );
        }

        cartRepository.delete(cart);

        return "Cart item removed successfully";
    }

    // CLEAR CURRENT USER CART
    public String clearCart(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        List<Cart> cartItems = cartRepository.findByUser(user);

        cartRepository.deleteAll(cartItems);

        return "Cart cleared successfully";
    }
}