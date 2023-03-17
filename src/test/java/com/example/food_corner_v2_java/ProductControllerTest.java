package com.example.food_corner_v2_java;

import com.example.food_corner_v2_java.auth.JwtService;
import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.dto.RestaurantDTO;
import com.example.food_corner_v2_java.model.enums.UserRolesEnum;
import com.example.food_corner_v2_java.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private AppUser appUser;

    @MockBean
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        AppUser appUser = new AppUser()
                .setName("test")
                .setEmail("test@abv.bg")
                .setPassword("test")
                .setUserRole(UserRolesEnum.ADMIN);
        when(jwtService.generateToken(appUser)).thenReturn("my_jwt_token");
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpassword", authorities = {"ROLE_ADMIN"})
    public void testCreateProduct() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test".getBytes());

        when(productService.createProduct(any(), anyString(), anyString(), anyDouble(), any(), anyString(), anyLong(), eq("testuser")))
                .thenReturn(new RestaurantDTO());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/food-corner/products/1/add-product")
                        .file(file)
                        .param("name", "test")
                        .param("ingredients", "test")
                        .param("weight", "1.0")
                        .param("price", "10.0")
                        .param("category", "test")
                        .header("Authorization", "Bearer my_jwt_token"))
                .andExpect(status().isCreated())
                .andReturn();

        verify(productService, times(1)).createProduct(any(), anyString(), anyString(), anyDouble(), any(), anyString(), anyLong(), eq("testuser"));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpassword", authorities = {"ROLE_ADMIN"})
    public void testEditProduct() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test".getBytes());

        when(productService.editProduct(any(), anyString(), anyString(), anyDouble(), any(), anyString(), anyLong(), anyLong(), eq("testuser")))
                .thenReturn(new RestaurantDTO());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/food-corner/products/1/edit-product/1")
                        .file(file)
                        .param("name", "test")
                        .param("ingredients", "test")
                        .param("weight", "1.0")
                        .param("price", "10.0")
                        .param("category", "test")
                        .header("Authorization", "Bearer my_jwt_token"))
                .andExpect(status().isOk())
                .andReturn();

        verify(productService, times(1)).editProduct(any(), anyString(), anyString(), anyDouble(), any(), anyString(), anyLong(), anyLong(), eq("testuser"));
    }

}