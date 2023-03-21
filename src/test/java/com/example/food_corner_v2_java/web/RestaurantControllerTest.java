package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.errors.AppException;
import com.example.food_corner_v2_java.model.dto.RestaurantDTO;
import com.example.food_corner_v2_java.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    private Principal principal;

    @BeforeEach
    void setUp() {
        principal = mock(Principal.class);
    }

    @Test
    void editRestaurant() {
        MultipartFile image = mock(MultipartFile.class);
        when(principal.getName()).thenReturn("testUser");
        ResponseEntity<RestaurantDTO> response = restaurantController
                .editRestaurant(1L, 1L, "name", "address", "category", "city", "workingHours", image, principal);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void editRestaurantMissingRequiredFields() {
        MultipartFile image = mock(MultipartFile.class);
        assertThrows(AppException.class, () -> restaurantController.editRestaurant(1L, 1L, "", "address", "category", "city", "workingHours", image, principal));
    }

    @Test
    void editRestaurantNotLoggedIn() {
        MultipartFile image = mock(MultipartFile.class);
        when(principal.getName()).thenReturn("");
        assertThrows(AppException.class, () -> restaurantController.editRestaurant(1L, 1L, "name", "address", "category", "city", "workingHours", image, principal));
    }

    @Test
    public void getAllRestaurantsTest() throws Exception {
        mockMvc.perform(get("/api/food-corner/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getRestaurantByIdTest() throws Exception {
        Long restaurantId = 1L;
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(restaurantId);

        mockMvc.perform(get("/api/food-corner/restaurants/{id}", restaurantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(restaurantId));
    }

    @Test
    public void testGetOwnRestaurants() throws Exception {
        RestaurantDTO restaurant1 = new RestaurantDTO();
        restaurant1.setId(1L);
        restaurant1.setName("Restaurant 1");

        RestaurantDTO restaurant2 = new RestaurantDTO();
        restaurant2.setId(2L);
        restaurant2.setName("Restaurant 2");

        List<RestaurantDTO> restaurants = Arrays.asList(restaurant1, restaurant2);

        given(restaurantService.getOwnRestaurants("user@example.com")).willReturn(restaurants);

        mockMvc.perform(get("/api/food-corner/restaurants/by-owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("user@example.com")))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRestaurantSuccess() {
        when(principal.getName()).thenReturn("testUser");
        ResponseEntity<String> response = restaurantController.deleteRestaurant(1L, 1L, principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Restaurant deleted successfully", response.getBody());
    }

    @Test
    void deleteRestaurantNotLoggedIn() {
        when(principal.getName()).thenReturn("");
        assertThrows(AppException.class, () -> restaurantController.deleteRestaurant(1L, 1L, principal));
    }


}
