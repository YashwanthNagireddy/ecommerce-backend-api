package com.yashwanth.ecommerce.dto;

public class OrderItemResponseDTO {

    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;


    public OrderItemResponseDTO(
            Long productId,
            String productName,
            Integer quantity,
            Double price
    ) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }


    public Long getProductId() {
        return productId;
    }


    public String getProductName() {
        return productName;
    }


    public Integer getQuantity() {
        return quantity;
    }


    public Double getPrice() {
        return price;
    }
}