package com.example.food_corner_v2_java.controllers;

import com.example.food_corner_v2_java.model.dto.RestaurantDTO;
import com.example.food_corner_v2_java.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void getAllRestaurantsTest() throws Exception {
        List<RestaurantDTO> restaurantDTOS = new ArrayList<>();
        restaurantDTOS.add(new RestaurantDTO());
        restaurantDTOS.add(new RestaurantDTO());

        Mockito.when(restaurantService.findAll()).thenReturn(restaurantDTOS);

        mockMvc.perform(get("/api/food-corner/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getRestaurantByIdTest() throws Exception {
        Long restaurantId = 1L;
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(restaurantId);

        Mockito.when(restaurantService.restaurantDTObyId(anyLong())).thenReturn(restaurantDTO);

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
    public void testCreateRestaurant() throws Exception {
        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setId(1L);
        restaurant.setName("Restaurant 1");

        given(restaurantService.createRestaurant(anyString(), anyString(), anyString(), anyString(), anyString(), any(), anyString())).willReturn(restaurant);

        MockMultipartFile imageFile = new MockMultipartFile("image", "test.png", "image/png", "test".getBytes());

        mockMvc.perform(multipart("/api/food-corner/restaurants/new-restaurant")
                        .file("image", imageFile.getBytes())
                        .param("name", "Restaurant 1")
                        .param("address", "123 Main St.")
                        .param("category", "Italian")
                        .param("city", "New York")
                        .param("workingHours", "8am-10pm")
                        .with(user("user@example.com")))
                .andExpect(status().isOk());
    }

}
