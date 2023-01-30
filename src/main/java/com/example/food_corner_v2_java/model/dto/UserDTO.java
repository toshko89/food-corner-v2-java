package com.example.food_corner_v2_java.model.dto;


import com.example.food_corner_v2_java.model.enums.UserRolesEnum;

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String city;
    private String address;
    private UserRolesEnum userRole;

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public UserDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public UserRolesEnum getUserRole() {
        return userRole;
    }

    public UserDTO setUserRole(UserRolesEnum userRole) {
        this.userRole = userRole;
        return this;
    }

    @Override
    public String toString() {
        return "id:" + id +
                ", name:'" + name + '\'' +
                ", email:'" + email + '\'' +
                ", phone:'" + phone + '\'' +
                ", city:'" + city + '\'' +
                ", address:'" + address + '\'' +
                ", userRole:" + userRole +
                '}';
    }
}
