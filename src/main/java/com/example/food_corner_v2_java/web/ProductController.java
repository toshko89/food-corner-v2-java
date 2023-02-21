package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.model.dto.RestaurantDTO;
import com.example.food_corner_v2_java.service.ProductService;
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
    public ResponseEntity<String> createProduct(
            @RequestParam("image") MultipartFile image,
            @RequestParam("name") String name,
            @RequestParam("ingredients") String ingredients,
            @RequestParam("weight") double weight,
            @RequestParam("price") BigDecimal price,
            @RequestParam("category") String category,
            @PathVariable Long restaurantId,
            Principal principal
    ) {
        System.out.println(image);
        //TODO: add validation finish this method
        this.productService.createProduct(image, name, ingredients, weight, price, category, restaurantId, principal.getName());

        return null;
    }

}
