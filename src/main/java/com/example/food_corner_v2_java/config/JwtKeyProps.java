package com.example.food_corner_v2_java.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt-secret")
public record JwtKeyProps(String key) {
}
