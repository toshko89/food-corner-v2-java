package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.model.Product;
import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.dto.RestaurantDTO;
import com.example.food_corner_v2_java.repository.RestaurantRepository;
import com.example.food_corner_v2_java.utils.CloudinaryImage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AppUserService appUserService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, AppUserService appUserService, ProductService productService, ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.appUserService = appUserService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    public void initRestaurantDB() {
        if (this.restaurantRepository.count() == 0) {
            AppUser appUser = this.appUserService.getUserByEmail("todor@abv.bg");
            Product pizza = this.productService.findByName("Pizza");

            Restaurant restaurant = new Restaurant()
                    .setName("Djikov")
                    .setAddress("GEo Milev")
                    .setCity("Sofia")
                    .setOwner(appUser)
                    .setRating(4)
                    .setRatingsCount(2)
                    .setWorkingHours("10:00-22:00")
                    .setImageUrl(new CloudinaryImage("https://res.cloudinary.com/dl72c1rli/image/upload/v1649517780/ju79tephlqlbwzjonb8b.jpg", "ju79tephlqlbwzjonb8b"))
                    .setCategory("Italian")
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
    public List<RestaurantDTO> findAll() {
        var restaurants = this.restaurantRepository.findAll();
        return this.restaurantRepository.findAll()
                .stream()
                .map(restaurant -> this.modelMapper.map(restaurant, RestaurantDTO.class))
                .toList();


    }

    public Restaurant findById(long restaurantId) {
        return this.restaurantRepository.findById(restaurantId).orElse(null);
    }
}
