package com.yashwanth.ecommerce.repository;

import com.yashwanth.ecommerce.entity.Order;
import com.yashwanth.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}