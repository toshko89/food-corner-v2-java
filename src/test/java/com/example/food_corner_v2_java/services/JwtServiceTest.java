package com.example.food_corner_v2_java.services;

import com.example.food_corner_v2_java.auth.JwtKeyProps;
import com.example.food_corner_v2_java.auth.JwtService;
import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.enums.UserRolesEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @MockBean
    private JwtKeyProps jwtKeyProps;

    private AppUser testUser;

    @BeforeEach
    public void setUp() {
        when(jwtKeyProps.getKey()).thenReturn("c620698b6f994b378ea0192cb491177259e34b4aaf6ae9681370e6677f2f21d3");
        testUser = new AppUser()
                .setId(1000L)
                .setName("test")
                .setEmail("test@abv.bg")
                .setPassword("test")
                .setUserRole(UserRolesEnum.ADMIN);
    }

    @Test
    public void generateTokenAndExtractUserEmail() {
        String token = jwtService.generateToken(testUser);
        String userEmail = jwtService.extractUserEmail(token);
        assertEquals(testUser.getUsername(), userEmail);
    }

    @Test
    public void validateToken() {
        String token = jwtService.generateToken(testUser);
        assertTrue(jwtService.validateToken(token, testUser));
    }

    @Test
    public void isTokenExpired() {
        String token = jwtService.generateToken(testUser);
        assertFalse(jwtService.isTokenExpired(token));
    }
}
