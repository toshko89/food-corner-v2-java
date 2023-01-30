package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.model.dto.RestaurantDTO;
import com.example.food_corner_v2_java.service.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/food-corner")
@CrossOrigin(origins = "http://localhost:3000/",allowCredentials = "true")
public class HomeController {

    private final RestaurantService restaurantService;

    public HomeController(RestaurantService restaurantService, ModelMapper modelMapper) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDTO>> restaurantResponseEntity() {
        List<RestaurantDTO> restaurants = this.restaurantService.findAll();
        return ResponseEntity.ok(restaurants);
    }

}
