package com.yashwanth.ecommerce.controller;

import com.yashwanth.ecommerce.entity.Order;
import com.yashwanth.ecommerce.entity.User;
import com.yashwanth.ecommerce.repository.OrderRepository;
import com.yashwanth.ecommerce.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public UserController(
            UserRepository userRepository,
            OrderRepository orderRepository
    ) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    // =========================
    // GET ALL USERS
    // =========================
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {

        return ResponseEntity.ok(
                userRepository.findAll()
        );
    }

    // =========================
    // GET USER BY ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(
            @PathVariable Long id
    ) {

        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // =========================
    // DELETE USER
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id
    ) {

        // Check whether user exists
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        // Check whether user has existing orders
        List<Order> orders = orderRepository.findByUser(user);

        if (!orders.isEmpty()) {

            return ResponseEntity
                    .status(409)
                    .body(
                            "User cannot be deleted because the user has existing orders."
                    );
        }

        // Delete user if there are no orders
        userRepository.delete(user);

        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }
}