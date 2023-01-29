package com.example.food_corner_v2_java.model.dto;

import com.example.food_corner_v2_java.validation.annotations.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterDTO {

    @UniqueEmail
    @Email(regexp = "[a-z0-9!#$%&'*+=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
            , message = "Invalid email address")
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters long")
    private String password;

    @NotBlank
    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters long")
    private String repeatPassword;

    public RegisterDTO() {
    }

    public String getEmail() {
        return email;
    }

    public RegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public RegisterDTO setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
        return this;
    }
}
