package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.errors.AppException;
import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.Product;
import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.model.dto.RestaurantDTO;
import com.example.food_corner_v2_java.repository.RestaurantRepository;
import com.example.food_corner_v2_java.utils.CloudinaryImage;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AppUserService appUserService;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    public RestaurantService(RestaurantRepository restaurantRepository, AppUserService appUserService,
                             ProductService productService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.restaurantRepository = restaurantRepository;
        this.appUserService = appUserService;
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    public Restaurant findByName(String name) {
        return this.restaurantRepository.findByName(name);

    }

    public List<RestaurantDTO> findAll() {
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

    public RestaurantDTO editRestaurant(
            Long restaurantId, String name,
            String address, String category,
            String city, String workingHours,
            MultipartFile image) {

        try {

            Restaurant restaurant = this.restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Restaurant not found"));

            if (this.restaurantRepository.findByName(name) != null && !restaurant.getName().equals(name)) {
                throw new AppException(HttpStatus.BAD_REQUEST, "Restaurant with this name already exists");
            }

            cloudinaryService.deleteImage(restaurant.getImageUrl().getPublicId());

            CloudinaryImage cloudinaryImage = cloudinaryService.uploadImage(image);

            restaurant.setName(name)
                    .setAddress(address)
                    .setCategory(category)
                    .setCity(city)
                    .setWorkingHours(workingHours)
                    .setImageUrl(cloudinaryImage);

            this.restaurantRepository.save(restaurant);
            return this.modelMapper.map(restaurant, RestaurantDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public boolean deleteRestaurant(Long id) {
        try {
            Restaurant restaurant = this.restaurantRepository.findById(id)
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Restaurant not found"));

            restaurant.getProducts().forEach(product -> {
                this.cloudinaryService.deleteImage(product.getImageUrl().getPublicId());
            });

            cloudinaryService.deleteImage(restaurant.getImageUrl().getPublicId());

            this.restaurantRepository.delete(restaurant);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public List<RestaurantDTO> getFavoriteRestaurants(List<String> idsList) {

        Iterable<Long> longIds = idsList.stream()
                .map(Long::valueOf) // Convert String elements to Long elements
                .collect(Collectors.toList());

        return this.restaurantRepository.findAllById(longIds)
                .stream()
                .map(restaurant -> this.modelMapper.map(restaurant, RestaurantDTO.class))
                .collect(Collectors.toList());
    }

    public void initRestaurantDB() {
        if (this.restaurantRepository.count() == 0) {
            AppUser appUser = this.appUserService.getUserByEmail("todor@abv.bg");
            Product pizza = this.productService.findByName("Pizza");

            Restaurant restaurant = new Restaurant()
                    .setName("Djikov")
                    .setAddress("Geo Milev")
                    .setCity("Sofia")
                    .setOwner(appUser)
                    .setRating(4)
                    .setRatingsCount(1)
                    .setWorkingHours("10:00-22:00")
                    .setImageUrl(new CloudinaryImage("https://res.cloudinary.com/dl72c1rli/image/upload/v1649517780/ju79tephlqlbwzjonb8b.jpg", "ju79tephlqlbwzjonb8b"))
                    .setCategory("Italian")
                    .setProducts(pizza)
                    .setProducts(pizza)
                    .setProducts(pizza);
            this.restaurantRepository.save(restaurant);
        }
    }

    public void save(Restaurant restaurant) {
        this.restaurantRepository.save(restaurant);
    }
}

