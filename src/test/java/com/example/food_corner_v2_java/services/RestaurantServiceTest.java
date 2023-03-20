package com.example.food_corner_v2_java.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.model.dto.RestaurantDTO;
import com.example.food_corner_v2_java.service.AppUserService;
import com.example.food_corner_v2_java.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

// Add any other necessary imports here

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private AppUserService appUserService;

    @Test
    public void testFindByName() {
        Restaurant foundRestaurant = restaurantService.findByName("Djikov");
        assertNotNull(foundRestaurant);
        assertEquals("Djikov", foundRestaurant.getName());
    }


    @Test
    public void testFindAll() {
        List<RestaurantDTO> foundRestaurants = restaurantService.findAll();
        assertNotNull(foundRestaurants);
        assertTrue(foundRestaurants.size() >= 1);

    }

    @Test
    public void testFindById() {
        Restaurant foundRestaurant = restaurantService.findById(1L);
        assertNotNull(foundRestaurant);
        assertEquals("Djikov", foundRestaurant.getName());
    }


    @Test
    public void testGetOwnRestaurants() {
        String ownerEmail = "todor@abv.bg";
        Restaurant testRestaurant1 = createTestRestaurant("Test Restaurant 1");
        Restaurant testRestaurant2 = createTestRestaurant("Test Restaurant 2");

        List<RestaurantDTO> ownRestaurants = restaurantService.getOwnRestaurants(ownerEmail);

        assertNotNull(ownRestaurants);
        assertTrue(ownRestaurants.size() >= 2);

        restaurantService.deleteRestaurant(testRestaurant1.getId());
        restaurantService.deleteRestaurant(testRestaurant2.getId());
    }

    @Test
    public void testRestaurantDTObyId() {
        Restaurant testRestaurant = createTestRestaurant("Test Restaurant");
        Long testRestaurantId = testRestaurant.getId();

        RestaurantDTO restaurantDTO = restaurantService.restaurantDTObyId(testRestaurantId);

        assertNotNull(restaurantDTO);
        assertEquals(testRestaurantId, restaurantDTO.getId());

        restaurantService.deleteRestaurant(testRestaurantId);
    }

    @Test
    public void testCreateRestaurant() {
        String ownerEmail = "todor@abv.bg";
        AppUser appUser = appUserService.getUserByEmail(ownerEmail);

        RestaurantDTO newRestaurant = restaurantService
                .createRestaurant("Test Restaurant", "Test Address", "Test Category",
                        "Test City", "10:00-22:00", null, appUser.getEmail());

        assertNotNull(newRestaurant);
        assertEquals("Test Restaurant", newRestaurant.getName());
        assertEquals("Test Address", newRestaurant.getAddress());
        assertEquals("Test Category", newRestaurant.getCategory());
        assertEquals("Test City", newRestaurant.getCity());
        assertEquals("10:00-22:00", newRestaurant.getWorkingHours());
        this.restaurantService.deleteRestaurant(newRestaurant.getId());
    }

    @Test
    public void testEditRestaurant() {
        String originalName = "Original Test Restaurant";
        String updatedName = "Updated Test Restaurant";
        String updatedAddress = "Updated Test Address";
        String updatedCategory = "Updated Test Category";
        String updatedCity = "Updated Test City";
        String updatedWorkingHours = "11:00-23:00";

        Restaurant testRestaurant = createTestRestaurant(originalName);
        Long testRestaurantId = testRestaurant.getId();

        RestaurantDTO editedRestaurant = restaurantService.editRestaurant(
                testRestaurantId,
                updatedName,
                updatedAddress,
                updatedCategory,
                updatedCity,
                updatedWorkingHours,
               null
        );

        assertNotNull(editedRestaurant);
        assertEquals(updatedName, editedRestaurant.getName());
        assertEquals(updatedAddress, editedRestaurant.getAddress());
        assertEquals(updatedCategory, editedRestaurant.getCategory());
        assertEquals(updatedCity, editedRestaurant.getCity());
        assertEquals(updatedWorkingHours, editedRestaurant.getWorkingHours());

        restaurantService.deleteRestaurant(editedRestaurant.getId());
    }

    @Test
    public void testDeleteRestaurant() {
        String testName = "Test Restaurant";
        Restaurant testRestaurant = createTestRestaurant(testName);
        Long testRestaurantId = testRestaurant.getId();

        boolean isDeleted = restaurantService.deleteRestaurant(testRestaurantId);

        assertTrue(isDeleted);
        assertNull(restaurantService.findById(testRestaurantId));
    }

    @Test
    public void testGetFavoriteRestaurants() {
        Restaurant testRestaurant1 = createTestRestaurant("Test Restaurant 1");
        Restaurant testRestaurant2 = createTestRestaurant("Test Restaurant 2");

        List<String> favoriteRestaurantIds = List.of(
                String.valueOf(testRestaurant1.getId()),
                String.valueOf(testRestaurant2.getId())
        );

        List<RestaurantDTO> favoriteRestaurants = restaurantService.getFavoriteRestaurants(favoriteRestaurantIds);

        assertNotNull(favoriteRestaurants);
        assertEquals(2, favoriteRestaurants.size());

        List<Long> returnedIds = favoriteRestaurants.stream()
                .map(RestaurantDTO::getId)
                .toList();

        assertTrue(returnedIds.contains(testRestaurant1.getId()));
        assertTrue(returnedIds.contains(testRestaurant2.getId()));

        restaurantService.deleteRestaurant(testRestaurant1.getId());
        restaurantService.deleteRestaurant(testRestaurant2.getId());
    }

    @Test
    public void testInitRestaurantDB() {
        // Write your test code here
    }

    private Restaurant createTestRestaurant(String name) {
        // Replace with appropriate email or create a test user
        String ownerEmail = "todor@abv.bg";
        AppUser appUser = appUserService.getUserByEmail(ownerEmail);

        Restaurant restaurant = new Restaurant()
                .setName(name)
                .setAddress("Test Address")
                .setCity("Test City")
                .setOwner(appUser)
                .setWorkingHours("10:00-22:00")
                .setCategory("Test Category");

        restaurantService.save(restaurant);
        return restaurant;
    }
}
