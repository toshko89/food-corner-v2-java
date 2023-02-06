package com.example.food_corner_v2_java.model.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;

public class ChangeUserDataDTO {

    @NotBlank
    private String name;

    @NotBlank
    @Digits(integer = 10, fraction = 0)
    private String phone;

    @NotBlank
    private String city;

    @NotBlank
    private String address;

    public ChangeUserDataDTO() {
    }

    public String getName() {
        return name;
    }

    public ChangeUserDataDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public ChangeUserDataDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ChangeUserDataDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public ChangeUserDataDTO setAddress(String address) {
        this.address = address;
        return this;
    }
}
