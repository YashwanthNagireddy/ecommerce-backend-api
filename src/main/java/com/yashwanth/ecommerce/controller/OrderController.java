package com.yashwanth.ecommerce.controller;

import com.yashwanth.ecommerce.dto.OrderResponseDTO;
import com.yashwanth.ecommerce.entity.Order;
import com.yashwanth.ecommerce.service.OrderService;

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



    // PLACE ORDER
    @PostMapping("/place")
    public Order placeOrder(Authentication authentication) {


        String email = authentication.getName();


        return orderService.placeOrder(email);

    }




    // GET MY ORDERS
    @GetMapping("/my-orders")
    public List<OrderResponseDTO> getMyOrders(
            Authentication authentication
    ) {


        String email = authentication.getName();


        return orderService.getOrdersByUser(email);

    }



    // GET ORDER BY ID
    @GetMapping("/{id}")
    public Order getOrderById(
            @PathVariable Long id
    ) {


        return orderService.getOrderById(id);

    }



    // GET ALL ORDERS
    @GetMapping
    public List<Order> getAllOrders() {

        return orderService.getAllOrders();

    }

}