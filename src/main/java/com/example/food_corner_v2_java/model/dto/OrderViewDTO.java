package com.example.food_corner_v2_java.model.dto;

import java.time.LocalDate;

public class OrderViewDTO {
    private Long id;
    private UserDTO buyer;
    private RestaurantOrderDTO restaurant;
    private ProductDTO product;
    private Integer quantity;
    private LocalDate date;

    public OrderViewDTO() {
    }

    public Long getId() {
        return id;
    }

    public OrderViewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public UserDTO getBuyer() {
        return buyer;
    }

    public OrderViewDTO setBuyer(UserDTO buyer) {
        this.buyer = buyer;
        return this;
    }

    public RestaurantOrderDTO getRestaurant() {
        return restaurant;
    }

    public OrderViewDTO setRestaurant(RestaurantOrderDTO restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public OrderViewDTO setProduct(ProductDTO product) {
        this.product = product;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderViewDTO setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public OrderViewDTO setDate(LocalDate date) {
        this.date = date;
        return this;
    }
}
