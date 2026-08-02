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

import org.springframework.stereotype.Service;

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



    // PLACE ORDER
    public Order placeOrder(String email) {


        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );


        List<Cart> cartItems = cartRepository.findByUser(user);



        if(cartItems.isEmpty()) {

            throw new RuntimeException("Cart is empty");

        }



        double total = 0;


        List<OrderItem> orderItems = new ArrayList<>();



        for(Cart cart : cartItems) {


            double price = cart.getProduct().getPrice();


            total += price * cart.getQuantity();



            OrderItem item = new OrderItem();


            item.setProductId(cart.getProduct().getId());

            item.setProductName(cart.getProduct().getName());

            item.setQuantity(cart.getQuantity());

            item.setPrice(price);



            orderItems.add(item);

        }



        Order order = new Order();


        order.setUser(user);

        order.setStatus("PLACED");

        order.setTotalAmount(total);



        for(OrderItem item : orderItems) {

            item.setOrder(order);

        }


        order.setItems(orderItems);



        Order savedOrder = orderRepository.save(order);



        cartRepository.deleteAll(cartItems);



        return savedOrder;

    }





    // GET USER ORDERS WITHOUT PASSWORD
    public List<OrderResponseDTO> getOrdersByUser(String email) {


        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );



        List<Order> orders = orderRepository.findByUser(user);



        List<OrderResponseDTO> response = new ArrayList<>();



        for(Order order : orders) {


            List<OrderItemResponseDTO> items = new ArrayList<>();


            for(OrderItem item : order.getItems()) {


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




    // GET ORDER BY ID
    public Order getOrderById(Long id) {


        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found")
                );

    }





    // GET ALL ORDERS
    public List<Order> getAllOrders() {

        return orderRepository.findAll();

    }

}