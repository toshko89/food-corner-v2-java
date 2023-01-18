package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class HomeController {

    private final RestaurantService restaurantService;

    public HomeController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/food-corner")
    public ResponseEntity<List<Restaurant>> restaurantResponseEntity() {
        List<Restaurant> restaurants = this.restaurantService.findAll();
        System.out.println(restaurants);
        return ResponseEntity.ok(restaurants);
    }

}
