package com.yashwanth.ecommerce.controller;

import com.yashwanth.ecommerce.entity.OrderItem;
import com.yashwanth.ecommerce.repository.OrderItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    private final OrderItemRepository orderItemRepository;

    public OrderItemController(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }


    // CREATE ORDER ITEM
    @PostMapping
    public OrderItem createOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }


    // GET ALL ORDER ITEMS
    @GetMapping
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }


    // DELETE ORDER ITEM
    @DeleteMapping("/{id}")
    public String deleteOrderItem(@PathVariable Long id) {

        orderItemRepository.deleteById(id);

        return "Order Item deleted successfully";
    }
}