package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.errors.AppException;
import com.example.food_corner_v2_java.model.dto.RestaurantDTO;
import com.example.food_corner_v2_java.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping(path = "/restaurants/new-restaurant")
    public ResponseEntity<RestaurantDTO> createRestaurant(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("category") String category,
            @RequestParam("city") String city,
            @RequestParam("workingHours") String workingHours,
            @RequestParam("image") MultipartFile image,
            Principal principal) {

        if (image.isEmpty() || name.isEmpty()
                || address.isEmpty() || category.isEmpty()
                || city.isEmpty() || workingHours.isEmpty()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "All fields are required");
        }

        if (principal.getName().isEmpty()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "You are not logged in");
        }

        RestaurantDTO restaurant = this.restaurantService
                .createRestaurant(name, address, category, city, workingHours, image, principal.getName());

        return ResponseEntity.ok(restaurant);
    }


    private Map<String, String> errors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> errors.put("error", fieldError.getDefaultMessage()));
        return errors;
    }

}
