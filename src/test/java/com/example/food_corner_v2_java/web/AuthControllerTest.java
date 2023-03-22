package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.model.dto.ChangeUserDataDTO;
import com.example.food_corner_v2_java.model.dto.LoginDTO;
import com.example.food_corner_v2_java.model.dto.RegisterDTO;
import com.example.food_corner_v2_java.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    @WithMockUser(username = "testuser", password = "testpassword", authorities = {"ROLE_ADMIN"})
    public void testRegister() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("test@example.com");
        registerDTO.setPassword("test123");
        registerDTO.setRepeatPassword("test123");

        mockMvc.perform(post("/api/food-corner/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpassword", authorities = {"ROLE_ADMIN"})
    public void testLogin() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("test123");

        mockMvc.perform(post("/api/food-corner/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpassword", authorities = {"ROLE_ADMIN"})
    public void testLogout() throws Exception {
        mockMvc.perform(get("/api/food-corner/users/logout"))
                .andExpect(status().isOk());
    }
    @Test
    @WithAnonymousUser
    void registerInvalidData() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("newuser@example.com");
        registerDTO.setPassword("password");
        registerDTO.setRepeatPassword("wrongpassword");

        mockMvc.perform(post("/api/food-corner/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "todor@abv.bg", password = "testpassword", authorities = {"ROLE_ADMIN"})
    void changeUserData_ValidData() throws Exception {
        ChangeUserDataDTO changeUserDataDTO = new ChangeUserDataDTO();
        changeUserDataDTO.setName("NewFirstName");

        Map<String, Object> expectedAppUser = new HashMap<>();
        expectedAppUser.put("id", 1L);
        expectedAppUser.put("email", "user@example.com");
        expectedAppUser.put("firstName", "NewFirstName");
        expectedAppUser.put("lastName", "NewLastName");
        expectedAppUser.put("token", "some.jwt.token");

        when(appUserService.editUser(ArgumentMatchers.eq(1L), any(ChangeUserDataDTO.class))).thenReturn(expectedAppUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/food-corner/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeUserDataDTO)))
                .andExpect(status().is4xxClientError());
    }
}
