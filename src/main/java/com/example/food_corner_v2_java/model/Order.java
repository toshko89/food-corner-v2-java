package com.example.food_corner_v2_java.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User buyer;

    @OneToOne
    private Restaurant restaurant;

    @OneToMany
    private List<Product> product;

    @Column(columnDefinition = "DATE")
    @CreationTimestamp
    private LocalDate date;

    public Order() {
    }

    public Long getId() {
        return id;
    }


    public User getBuyer() {
        return buyer;
    }

    public Order setBuyer(User buyer) {
        this.buyer = buyer;
        return this;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Order setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public List<Product> getProduct() {
        return product;
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
}
