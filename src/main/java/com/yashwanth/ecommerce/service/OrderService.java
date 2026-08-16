package com.yashwanth.ecommerce.service;

import com.yashwanth.ecommerce.dto.OrderItemResponseDTO;
import com.yashwanth.ecommerce.dto.OrderResponseDTO;
import com.yashwanth.ecommerce.entity.Cart;
import com.yashwanth.ecommerce.entity.Order;
import com.yashwanth.ecommerce.entity.OrderItem;
import com.yashwanth.ecommerce.entity.User;
import com.yashwanth.ecommerce.repository.CartRepository;
import com.yashwanth.ecommerce.repository.OrderRepository;
import com.yashwanth.ecommerce.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public OrderService(
            OrderRepository orderRepository,
            CartRepository cartRepository,
            UserRepository userRepository
    ) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }


    // =========================
    // PLACE ORDER
    // =========================

    @Transactional
    public Order placeOrder(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        List<Cart> cartItems =
                cartRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double total = 0;

        Order order = new Order();

        order.setUser(user);
        order.setStatus("PLACED");

        List<OrderItem> orderItems =
                new ArrayList<>();

        for (Cart cart : cartItems) {

            if (cart.getProduct() == null) {
                continue;
            }

            double price =
                    cart.getProduct().getPrice();

            total +=
                    price * cart.getQuantity();

            OrderItem item =
                    new OrderItem();

            item.setOrder(order);
            item.setProductId(
                    cart.getProduct().getId()
            );
            item.setProductName(
                    cart.getProduct().getName()
            );
            item.setQuantity(
                    cart.getQuantity()
            );
            item.setPrice(price);

            orderItems.add(item);
        }

        if (orderItems.isEmpty()) {
            throw new RuntimeException(
                    "Cart contains no valid products"
            );
        }

        order.setTotalAmount(total);
        order.setItems(orderItems);

        Order savedOrder =
                orderRepository.save(order);

        cartRepository.deleteAll(cartItems);

        return savedOrder;
    }


    // =========================
    // GET CURRENT USER ORDERS
    // =========================

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByUser(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        List<Order> orders =
                orderRepository.findByUser(user);

        List<OrderResponseDTO> response =
                new ArrayList<>();

        for (Order order : orders) {

            List<OrderItemResponseDTO> items =
                    new ArrayList<>();

            for (OrderItem item :
                    order.getItems()) {

                items.add(
                        new OrderItemResponseDTO(
                                item.getProductId(),
                                item.getProductName(),
                                item.getQuantity(),
                                item.getPrice()
                        )
                );
            }

            response.add(
                    new OrderResponseDTO(
                            order.getId(),
                            order.getStatus(),
                            order.getTotalAmount(),
                            items
                    )
            );
        }

        return response;
    }


    // =========================
    // GET ORDER BY ID
    //
    // USER  -> own order only
    // ADMIN -> any order
    // =========================

    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(
            Long id,
            String email,
            boolean isAdmin
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        Order order =
                orderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"
                                )
                        );

        // USER can access only their own order
        if (!isAdmin &&
                !order.getUser()
                        .getId()
                        .equals(user.getId())) {

            throw new AccessDeniedException(
                    "You are not authorized to view this order"
            );
        }

        List<OrderItemResponseDTO> items =
                new ArrayList<>();

        for (OrderItem item :
                order.getItems()) {

            items.add(
                    new OrderItemResponseDTO(
                            item.getProductId(),
                            item.getProductName(),
                            item.getQuantity(),
                            item.getPrice()
                    )
            );
        }

        return new OrderResponseDTO(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                items
        );
    }


    // =========================
    // GET ALL ORDERS
    // ADMIN ONLY
    // =========================

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }
}