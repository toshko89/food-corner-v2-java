package com.example.food_corner_v2_java.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt-secret")
public class JwtKeyProps {
    private String key;
    private String adminPassword;

    public String getKey() {
        return key;
    }

    public JwtKeyProps setKey(String key) {
        this.key = key;
        return this;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public JwtKeyProps setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
        return this;
    }
}
