package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.config.FirebaseConfig;
import com.example.food_corner_v2_java.errors.AppException;
import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.Product;
import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.model.dto.RestaurantDTO;
import com.example.food_corner_v2_java.repository.RestaurantRepository;
import com.example.food_corner_v2_java.utils.CloudinaryImage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AppUserService appUserService;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final FirebaseConfig firebaseConfig;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, AppUserService appUserService
            , ProductService productService, ModelMapper modelMapper, CloudinaryService cloudinaryService, FirebaseConfig firebaseConfig) {
        this.restaurantRepository = restaurantRepository;
        this.appUserService = appUserService;
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.firebaseConfig = firebaseConfig;
    }

    public Restaurant findByName(String name) {
        return this.restaurantRepository.findByName(name);

    }

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

    public RestaurantDTO restaurantDTObyId(long restaurantId) {
        Restaurant restaurant = this.restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        return this.modelMapper.map(restaurant, RestaurantDTO.class);
    }

    public List<RestaurantDTO> getOwnRestaurants(String name) {
        AppUser appUser = this.appUserService.getUserByEmail(name);
        return this.restaurantRepository.findAllByOwner(appUser)
                .stream()
                .map(restaurant -> this.modelMapper.map(restaurant, RestaurantDTO.class))
                .toList();
    }

    public RestaurantDTO createRestaurant(
            String name, String address,
            String category, String city,
            String workingHours, MultipartFile image,
            String ownerEmail) {

        try {
            AppUser appUser = this.appUserService.getUserByEmail(ownerEmail);

//      Firebase configuration for image upload to be deleted
//            Blob blob = firebaseConfig.getFirebaseStorage().create(image.getOriginalFilename(), image.getBytes(), image.getContentType());
//            boolean delete = firebaseConfig.getFirebaseStorage().getStorage().delete(blob.getGeneratedId());
//            Restaurant restaurant = new Restaurant()
//                    .setName(name)
//                    .setAddress(address)
//                    .setCategory(category)
//                    .setCity(city)
//                    .setWorkingHours(workingHours)
//                    .setOwner(appUser)
//                    .setImageUrl(new CloudinaryImage(blob.getMediaLink(), blob.getName()));

            CloudinaryImage cloudinaryImage = cloudinaryService.uploadImage(image);

            Restaurant restaurant = new Restaurant()
                    .setName(name)
                    .setAddress(address)
                    .setCategory(category)
                    .setCity(city)
                    .setWorkingHours(workingHours)
                    .setOwner(appUser)
                    .setImageUrl(cloudinaryImage);

            this.restaurantRepository.save(restaurant);
            return this.modelMapper.map(restaurant, RestaurantDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
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
}

