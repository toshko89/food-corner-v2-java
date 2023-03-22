package com.example.food_corner_v2_java.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AppUser buyer;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Restaurant> restaurant = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(columnDefinition = "DATETIME")
    @CreationTimestamp
    private LocalDate date;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public Order setId(Long id) {
        this.id = id;
        return this;
    }

    public AppUser getBuyer() {
        return buyer;
    }

    public Order setBuyer(AppUser buyer) {
        this.buyer = buyer;
        return this;
    }

    public List<Restaurant> getRestaurant() {
        return restaurant;
    }

    public Order setRestaurant(List<Restaurant> restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public List<Product> getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Order setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Order setProduct(List<Product> product) {
        this.product = product;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Order setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
