package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.model.Product;
import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.model.User;
import com.example.food_corner_v2_java.repository.RestaurantRepository;
import com.example.food_corner_v2_java.utils.CloudinaryImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, UserService userService, ProductService productService) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public void initRestaurantDB() {
        if (this.restaurantRepository.count() == 0) {
            User user = this.userService.getUserByEmail("todor@abv.bg");
            Product pizza = this.productService.findByName("Pizza");

            Restaurant restaurant = new Restaurant()
                    .setName("Djikov")
                    .setAddress("GEo Milev")
                    .setCity("Sofia")
                    .setOwner(user)
                    .setRating(4)
                    .setRatingsCount(2)
                    .setWorkingHours("10:00-22:00")
                    .setImageUrl(new CloudinaryImage("https://res.cloudinary.com/dl72c1rli/image/upload/v1649517780/ju79tephlqlbwzjonb8b.jpg", "ju79tephlqlbwzjonb8b"))
                    .setCategorie("Pizza")
                    .setProducts(pizza)
                    .setProducts(pizza)
                    .setProducts(pizza);
            this.restaurantRepository.save(restaurant);
        }
    }

    public Restaurant findByName(String name) {
        return this.restaurantRepository.findByName(name);

    }

    @Transactional
    public List<Restaurant> findAll() {
        List<Restaurant> all = this.restaurantRepository.findAll();
        System.out.println();

        return all;
    }

    public Restaurant findById(long restaurantId) {
        return this.restaurantRepository.findById(restaurantId).orElse(null);
    }
}
