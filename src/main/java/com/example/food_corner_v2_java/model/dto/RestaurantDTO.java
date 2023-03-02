package com.example.food_corner_v2_java.model.dto;

import com.example.food_corner_v2_java.model.Product;
import com.example.food_corner_v2_java.utils.CloudinaryImage;

import java.util.HashSet;
import java.util.Set;

public class RestaurantDTO {
    private Long id;
    private String name;
    private String category;
    private String city;
    private String address;
    private String workingHours;
    private UserDTO owner;
    private CloudinaryImage imageUrl;
    private Set<Product> products = new HashSet<>();
    private double rating;
    private Integer ratingsCount;

    public RestaurantDTO() {
    }

    public Long getId() {
        return id;
    }

    public RestaurantDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RestaurantDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public RestaurantDTO setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getCity() {
        return city;
    }

    public RestaurantDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public RestaurantDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public RestaurantDTO setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
        return this;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public RestaurantDTO setOwner(UserDTO owner) {
        this.owner = owner;
        return this;
    }

    public CloudinaryImage getImageUrl() {
        return imageUrl;
    }

    public RestaurantDTO setImageUrl(CloudinaryImage imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public RestaurantDTO setProducts(Set<Product> products) {
        this.products = products;
        return this;
    }

    public double getRating() {
        return rating;
    }

    public RestaurantDTO setRating(double rating) {
        this.rating = rating;
        return this;
    }

    public Integer getRatingsCount() {
        return ratingsCount;
    }

    public RestaurantDTO setRatingsCount(Integer ratingsCount) {
        this.ratingsCount = ratingsCount;
        return this;
    }
}
