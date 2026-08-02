package com.yashwanth.ecommerce.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    private String status;


    private Double totalAmount;


    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<OrderItem> items = new ArrayList<>();


    public Long getId() {
        return id;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public Double getTotalAmount() {
        return totalAmount;
    }


    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }


    public List<OrderItem> getItems() {
        return items;
    }


    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}