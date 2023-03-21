package com.example.food_corner_v2_java.services;

import com.example.food_corner_v2_java.model.dto.UserDTO;
import com.example.food_corner_v2_java.model.enums.UserRolesEnum;
import com.example.food_corner_v2_java.service.AppUserService;
import com.example.food_corner_v2_java.web.AdminController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AppUserService appUserService;

    @Mock
    private Principal principal;

    @Test
    public void testFindAllUsers() {
        UserDTO user1 = new UserDTO().setEmail("test123@abv.bg").setId(1L).setUserRole(UserRolesEnum.ADMIN);
        UserDTO user2 = new UserDTO().setEmail("test1234@abv.bg").setId(2L).setUserRole(UserRolesEnum.ADMIN);
        List<UserDTO> expectedUsers = Arrays.asList(user1, user2);
        when(principal.getName()).thenReturn("test123@abv.bg");
        when(appUserService.isAdmin(principal.getName())).thenReturn(true);
        when(appUserService.findAllUsers(principal.getName())).thenReturn(expectedUsers);

        ResponseEntity<List<UserDTO>> responseEntity = adminController.findAllUsers(principal);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedUsers, responseEntity.getBody());
    }

    @Test
    public void testMakeAdmin() {
        // Arrange
        UserDTO user = new UserDTO().setEmail("test123@abv.bg").setId(1L).setUserRole(UserRolesEnum.ADMIN);
        when(principal.getName()).thenReturn("test123@abv.bg");
        when(appUserService.isAdmin(principal.getName())).thenReturn(true);
        when(appUserService.editUserRole(any())).thenReturn(user);

        ResponseEntity<UserDTO> responseEntity = adminController.makeAdmin(4L, principal);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    // You can add more test cases for non-admin users or other scenarios.
}
