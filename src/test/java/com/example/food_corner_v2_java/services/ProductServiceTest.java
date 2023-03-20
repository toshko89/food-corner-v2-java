package com.example.food_corner_v2_java.services;

import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.Product;
import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.model.dto.RestaurantDTO;
import com.example.food_corner_v2_java.model.enums.UserRolesEnum;
import com.example.food_corner_v2_java.repository.ProductRepository;
import com.example.food_corner_v2_java.service.AppUserService;
import com.example.food_corner_v2_java.service.CloudinaryService;
import com.example.food_corner_v2_java.service.ProductService;
import com.example.food_corner_v2_java.service.RestaurantService;
import com.example.food_corner_v2_java.utils.CloudinaryImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CloudinaryService cloudinaryService;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private AppUserService appUserService;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    private MockMultipartFile image;
    private Restaurant restaurant;
    private Product product;
    private AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser = new AppUser().setEmail("principalName").setUserRole(UserRolesEnum.ADMIN);
        image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "test-image".getBytes());
        restaurant = new Restaurant().setId(1l).setOwner(appUser);
        product = new Product().setImageUrl(new CloudinaryImage("url", "publicId"));
    }

    @Test
    void testCreateProduct() throws IOException {
        when(restaurantService.findById(anyLong())).thenReturn(restaurant);
        when(cloudinaryService.uploadImage(any())).thenReturn(new CloudinaryImage());
        when(productRepository.save(any())).thenReturn(product);
        when(modelMapper.map(any(), any())).thenReturn(new RestaurantDTO());

        RestaurantDTO result = productService.createProduct(image, "Product", "Ingredient1,Ingredient2", 100,
                BigDecimal.valueOf(10), "Category", 1L, "principalName");

        verify(restaurantService, times(1)).findById(anyLong());
        verify(cloudinaryService, times(1)).uploadImage(any());
        verify(productRepository, times(1)).save(any());
        verify(modelMapper, times(1)).map(any(), any());

        assertEquals(RestaurantDTO.class, result.getClass());
    }

    @Test
    void testEditProduct() throws IOException {
        when(restaurantService.findById(anyLong())).thenReturn(restaurant);
        when(productRepository.findById(anyLong())).thenReturn(java.util.Optional.of(product));
        when(cloudinaryService.deleteImage("publicId")).thenReturn(true);
        when(cloudinaryService.uploadImage(any())).thenReturn(new CloudinaryImage());
        when(modelMapper.map(any(), any())).thenReturn(new RestaurantDTO());

        RestaurantDTO result = productService.editProduct(image, "Product", "Ingredient1,Ingredient2", 100,
                BigDecimal.valueOf(10), "Category", 1L, 1L, "principalName");

        verify(restaurantService, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findById(anyLong());
        verify(cloudinaryService, times(1)).deleteImage(anyString());
        verify(cloudinaryService, times(1)).uploadImage(any());
        verify(modelMapper, times(1)).map(any(), any());
    }
}
