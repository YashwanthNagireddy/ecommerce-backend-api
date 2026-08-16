package com.yashwanth.ecommerce.dto;

import java.util.List;

public class OrderResponseDTO {

    private Long id;
    private String status;
    private Double totalAmount;
    private List<OrderItemResponseDTO> items;

    public OrderResponseDTO(
            Long id,
            String status,
            Double totalAmount,
            List<OrderItemResponseDTO> items
    ) {
        this.id = id;
        this.status = status;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public List<OrderItemResponseDTO> getItems() {
        return items;
    }
}