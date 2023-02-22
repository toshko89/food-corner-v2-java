package com.example.food_corner_v2_java.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/food-corner/users/**")
                .permitAll()
                .requestMatchers("/api/food-corner/users/{id}")
                .authenticated()
                .requestMatchers("/api/food-corner/restaurants/**")
                .permitAll()
                .requestMatchers("/api/food-corner/restaurants/by-owner", "/api/food-corner/restaurants/new-restaurant")
                .authenticated()
                .requestMatchers("/api/food-corner/restaurants/{userId}/edit/{restaurantId}","/api/food-corner/restaurants/{userId}/delete/{restaurantId}")
                .authenticated()
                .requestMatchers("/api/food-corner/products/{restaurantId}/add-product","/api/food-corner/products/{restaurantId}/edit-product/{productId}", "/api/food-corner/products/{restaurantId}/delete-product/{productId}")
                .authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
