package com.example.food_corner_v2_java.model.dto;

import com.example.food_corner_v2_java.model.Product;
import com.example.food_corner_v2_java.utils.CloudinaryImage;

import java.util.HashSet;
import java.util.Set;

public class RestaurantOrderDTO {
    private Long id;
    private String name;
    private String category;
    private String city;
    private String address;
    private String workingHours;
    private CloudinaryImage imageUrl;

    public RestaurantOrderDTO() {
    }

    public Long getId() {
        return id;
    }

    public RestaurantOrderDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RestaurantOrderDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public RestaurantOrderDTO setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getCity() {
        return city;
    }

    public RestaurantOrderDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public RestaurantOrderDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public RestaurantOrderDTO setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
        return this;
    }

    public CloudinaryImage getImageUrl() {
        return imageUrl;
    }

    public RestaurantOrderDTO setImageUrl(CloudinaryImage imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
