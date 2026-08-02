package com.yashwanth.ecommerce.repository;

import com.yashwanth.ecommerce.entity.Cart;
import com.yashwanth.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUser(User user);

}