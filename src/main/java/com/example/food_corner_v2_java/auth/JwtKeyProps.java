package com.example.food_corner_v2_java.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt-secret")
public record JwtKeyProps(String key) {
}
