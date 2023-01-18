package com.example.food_corner_v2_java.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRoles> userRoles = new HashSet<>();

    @OneToMany
    private Set<Restaurant> restaurants;

    @OneToMany(mappedBy = "buyer")
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Comment> comments = new HashSet<>();

    public User() {
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public User setComments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public User setRestaurants(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
        return this;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public User setOrders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getCity() {
        return city;
    }

    public User setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public Set<UserRoles> getUserRoles() {
        return userRoles;
    }

    public User setUserRoles(Set<UserRoles> userRoles) {
        this.userRoles = userRoles;
        return this;
    }
}
