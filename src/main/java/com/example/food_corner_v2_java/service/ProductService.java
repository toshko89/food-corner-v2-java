package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.errors.AppException;
import com.example.food_corner_v2_java.model.Product;
import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.model.dto.RestaurantDTO;
import com.example.food_corner_v2_java.repository.ProductRepository;
import com.example.food_corner_v2_java.utils.CloudinaryImage;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final RestaurantService restaurantService;
    private final AppUserService appUserService;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, CloudinaryService cloudinaryService,
                          @Lazy RestaurantService restaurantService, AppUserService appUserService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.cloudinaryService = cloudinaryService;
        this.restaurantService = restaurantService;
        this.appUserService = appUserService;
        this.modelMapper = modelMapper;
    }

    public List<Product> findAllByName(String name) {
        return this.productRepository.findAllByName(name);
    }

    public Product findByName(String name) {
        return this.productRepository.findByName(name);
    }

    public RestaurantDTO createProduct(
            MultipartFile image, String name,
            String ingredients, double weight,
            BigDecimal price, String category,
            Long restaurantId, String principalName) {

        try {

            Restaurant restaurant = this.restaurantService.findById(restaurantId);

            if (!restaurant.getOwner().getUsername().equals(principalName) && !this.appUserService.isAdmin(principalName)) {
                throw new AppException(HttpStatus.FORBIDDEN, "You are not authorized to add products to this restaurant");
            }

            CloudinaryImage cloudinaryImage = cloudinaryService.uploadImage(image);

            Product product = new Product()
                    .setName(name)
                    .setIngredients(List.of(ingredients.split(",")))
                    .setWeight(weight)
                    .setPrice(price)
                    .setCategory(category)
                    .setImageUrl(cloudinaryImage);

            this.productRepository.save(product);

            restaurant.addProduct(product);

            return this.modelMapper.map(restaurant, RestaurantDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public RestaurantDTO editProduct(
            MultipartFile image, String name, String ingredients,
            double weight, BigDecimal price, String category,
            Long restaurantId, Long productId, String principalName) {

        try {
            Restaurant restaurant = this.restaurantService.findById(restaurantId);
            Product product = this.productRepository.findById(productId)
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Product not found"));

            if (!restaurant.getOwner().getUsername().equals(principalName) && !this.appUserService.isAdmin(principalName)) {
                throw new AppException(HttpStatus.FORBIDDEN, "You are not authorized to add products to this restaurant");
            }

            this.cloudinaryService.deleteImage(product.getImageUrl().getPublicId());
            CloudinaryImage cloudinaryImage = this.cloudinaryService.uploadImage(image);

            product.setName(name)
                    .setIngredients(List.of(ingredients.split(",")))
                    .setWeight(weight)
                    .setPrice(price)
                    .setCategory(category)
                    .setImageUrl(cloudinaryImage);

            return this.modelMapper.map(restaurant, RestaurantDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void initProductDB() {
        if (this.productRepository.count() == 0) {
            Product product = new Product()
                    .setName("Pizza")
                    .setPrice(BigDecimal.valueOf(15))
                    .setWeight(300)
                    .setCategory("Italian")
                    .setImageUrl(new CloudinaryImage("https://res.cloudinary.com/dl72c1rli/image/upload/v1649601451/p5i2hrgtl9a5drkopvmn.jpg", "p5i2hrgtl9a5drkopvmn"))
                    .setIngredients(List.of("Brashno", "Sirene"));
            this.productRepository.save(product);
        }
    }
}
