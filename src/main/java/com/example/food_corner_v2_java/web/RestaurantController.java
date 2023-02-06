package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.model.dto.RestaurantDTO;
import com.example.food_corner_v2_java.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/food-corner")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDTO>> restaurantResponseEntity() {
        List<RestaurantDTO> restaurants = this.restaurantService.findAll();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<RestaurantDTO> restaurantResponseEntity(@PathVariable Long id) {

        RestaurantDTO restaurant = this.restaurantService.restaurantDTObyId(id);

        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/restaurants/by-owner")
    public ResponseEntity<List<RestaurantDTO>> getOwnRestaurants(Principal principal) {

        List<RestaurantDTO> restaurants = this.restaurantService.getOwnRestaurants(principal.getName());

        return ResponseEntity.ok(restaurants);
    }


}
