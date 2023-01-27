package com.example.food_corner_v2_java.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@ConfigurationProperties(prefix = "jwt-secret")
public class JwtService {

    @Value("${secret}")
    private static String secret;

    public String extractUserEmail(String jwt) {
        return "userEmail";
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(jwt).getBody();
    }
}
