package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.errors.AppException;
import com.example.food_corner_v2_java.model.dto.RestaurantDTO;
import com.example.food_corner_v2_java.service.RestaurantService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/food-corner")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        try {
            List<RestaurantDTO> restaurants = this.restaurantService.findAll();
            return ResponseEntity.ok(restaurants);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable Long id) {
        try {
            RestaurantDTO restaurant = this.restaurantService.restaurantDTObyId(id);
            return ResponseEntity.ok(restaurant);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/restaurants/by-owner")
    public ResponseEntity<List<RestaurantDTO>> getOwnRestaurants(Principal principal) {
        try {
            List<RestaurantDTO> restaurants = this.restaurantService.getOwnRestaurants(principal.getName());
            return ResponseEntity.ok(restaurants);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
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

        if (this.restaurantService.findByName(name) != null) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Restaurant with this name already exists");
        }

        if (image.isEmpty() || name.isEmpty()
                || address.isEmpty() || category.isEmpty()
                || city.isEmpty() || workingHours.isEmpty()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "All fields are required");
        }

        if (principal.getName().isEmpty()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "You are not logged in");
        }

        try {
            RestaurantDTO restaurant = this.restaurantService.createRestaurant(name, address, category, city, workingHours, image, principal.getName());
            return ResponseEntity.ok(restaurant);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping(path = "/restaurants/{userId}/edit/{restaurantId}")
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<RestaurantDTO> editRestaurant(
            @PathVariable Long userId,
            @PathVariable Long restaurantId,
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

        try {
            RestaurantDTO restaurant = this.restaurantService
                    .editRestaurant(restaurantId, name, address, category, city, workingHours, image);
            return ResponseEntity.ok(restaurant);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/restaurants/{userId}/delete/{restaurantId}")
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteRestaurant(
            @PathVariable Long userId,
            @PathVariable Long restaurantId,
            Principal principal) {

        if (principal.getName().isEmpty()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "You are not logged in");
        }

        try {
            this.restaurantService.deleteRestaurant(restaurantId);
            return ResponseEntity.ok("Restaurant deleted successfully");
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @GetMapping("/restaurants/favorites")
    public ResponseEntity<List<RestaurantDTO>> getFavoriteRestaurants(@RequestParam("ids") String ids) {

        if (ids.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        List<String> idList = Arrays.stream(ids.split(",")).collect(Collectors.toList());

        try {
            List<RestaurantDTO> favoriteRestaurants = this.restaurantService.getFavoriteRestaurants(idList);

            return ResponseEntity.ok(favoriteRestaurants);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @NotNull
    private Map<String, String> errors(@NotNull BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> errors.put("error", fieldError.getDefaultMessage()));
        return errors;
    }

}
