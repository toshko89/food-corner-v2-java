package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.errors.AppException;
import com.example.food_corner_v2_java.model.dto.RestaurantDTO;
import com.example.food_corner_v2_java.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping("api/food-corner")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products/{restaurantId}/add-product")
    public ResponseEntity<RestaurantDTO> createProduct(
            @RequestParam("image") MultipartFile image,
            @RequestParam("name") String name,
            @RequestParam("ingredients") String ingredients,
            @RequestParam("weight") double weight,
            @RequestParam("price") BigDecimal price,
            @RequestParam("category") String category,
            @PathVariable Long restaurantId,
            Principal principal
    ) {

        if (image.isEmpty() || name.isEmpty() || ingredients.isEmpty() || category.isEmpty()
                || price == null || weight == 0 || restaurantId == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, "All fields are required");
        }

        if (principal.getName().isEmpty()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "You are not logged in");
        }

        try {
            RestaurantDTO restaurantDTO = this.productService
                    .createProduct(image, name, ingredients, weight, price, category, restaurantId, principal.getName());

            return ResponseEntity.created(null).body(restaurantDTO);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/products/{restaurantId}/edit-product/{productId}")
    public ResponseEntity<RestaurantDTO> editProduct(
            @RequestParam("image") MultipartFile image,
            @RequestParam("name") String name,
            @RequestParam("ingredients") String ingredients,
            @RequestParam("weight") double weight,
            @RequestParam("price") BigDecimal price,
            @RequestParam("category") String category,
            @PathVariable Long restaurantId,
            @PathVariable Long productId,
            Principal principal
    ) {

        if (image.isEmpty() || name.isEmpty() || ingredients.isEmpty() || category.isEmpty()
                || price == null || weight == 0 || restaurantId == null || productId == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, "All fields are required");
        }

        if (principal.getName().isEmpty()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "You are not logged in");
        }

        try {
            RestaurantDTO restaurantDTO = this.productService
                    .editProduct(image, name, ingredients, weight, price, category, restaurantId, productId, principal.getName());

            return ResponseEntity.ok(restaurantDTO);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/products/{restaurantId}/delete-product/{productId}")
    public ResponseEntity<RestaurantDTO> deleteProduct(
            @PathVariable Long restaurantId,
            @PathVariable Long productId,
            Principal principal
    ) {

        if (restaurantId == null || productId == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, "All fields are required");
        }

        if (principal.getName().isEmpty()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "You are not logged in");
        }

        try {
            RestaurantDTO restaurantDTO = this.productService
                    .deleteProduct(restaurantId, productId, principal.getName());

            return ResponseEntity.ok(restaurantDTO);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
