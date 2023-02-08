package com.example.food_corner_v2_java.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.File;

public class CreateRestaurantDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Category is mandatory")
    private String category;

    @NotBlank(message = "Phone is mandatory")
    private String city;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Working hours are mandatory")
    private String workingHours;

    @NotNull(message = "Image is mandatory")
    private File imageUrl;

    public CreateRestaurantDTO() {
    }

    public String getName() {
        return name;
    }

    public CreateRestaurantDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public CreateRestaurantDTO setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getCity() {
        return city;
    }

    public CreateRestaurantDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public CreateRestaurantDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public CreateRestaurantDTO setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
        return this;
    }

    public File getImageUrl() {
        return imageUrl;
    }

    public CreateRestaurantDTO setImageUrl(File imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
