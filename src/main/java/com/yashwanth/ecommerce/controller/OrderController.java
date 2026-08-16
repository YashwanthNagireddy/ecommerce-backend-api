package com.yashwanth.ecommerce.controller;

import com.yashwanth.ecommerce.dto.OrderResponseDTO;
import com.yashwanth.ecommerce.entity.Order;
import com.yashwanth.ecommerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // =========================
    // PLACE ORDER
    // USER + ADMIN
    // =========================

    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(
            Authentication authentication
    ) {

        String email = authentication.getName();

        Order order = orderService.placeOrder(email);

        return ResponseEntity.ok(order);
    }


    // =========================
    // GET MY ORDERS
    // USER + ADMIN
    // =========================

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                orderService.getOrdersByUser(email)
        );
    }


    // =========================
    // GET ORDER BY ID
    //
    // USER  -> only own order
    // ADMIN -> any order
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable Long id,
            Authentication authentication
    ) {

        String email = authentication.getName();

        boolean isAdmin =
                authentication.getAuthorities()
                        .stream()
                        .anyMatch(authority ->
                                authority.getAuthority()
                                        .equals("ROLE_ADMIN")
                        );

        return ResponseEntity.ok(
                orderService.getOrderById(
                        id,
                        email,
                        isAdmin
                )
        );
    }


    // =========================
    // GET ALL ORDERS
    // ADMIN ONLY
    // =========================

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }
}