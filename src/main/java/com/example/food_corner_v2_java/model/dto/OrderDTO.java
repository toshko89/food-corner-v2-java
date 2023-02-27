package com.example.food_corner_v2_java.model.dto;

import com.example.food_corner_v2_java.model.Product;

public class OrderDTO {

    private Long restaurantId;

    private Product product;

    private int quantity;

    public OrderDTO() {
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public OrderDTO setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public OrderDTO setProduct(Product product) {
        this.product = product;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderDTO setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
}
