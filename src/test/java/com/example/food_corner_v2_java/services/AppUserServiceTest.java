package com.example.food_corner_v2_java.services;

import com.example.food_corner_v2_java.auth.JwtKeyProps;
import com.example.food_corner_v2_java.auth.JwtService;
import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.dto.RegisterDTO;
import com.example.food_corner_v2_java.model.dto.UserDTO;
import com.example.food_corner_v2_java.model.enums.UserRolesEnum;
import com.example.food_corner_v2_java.repository.UserRepository;
import com.example.food_corner_v2_java.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class AppUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private JwtKeyProps jwtKeyProps;

    @InjectMocks
    private AppUserService appUserService;

    private AppUser sampleUser;

    private AppUser adminUser;
    private AppUser nonAdminUser;

    private RegisterDTO registerDTO;
    private UserDTO userDTO;
    private String token;
    private AppUser appUser;

    @BeforeEach
    void setUp() {
        sampleUser = new AppUser();
        sampleUser.setId(1001L);
        sampleUser.setEmail("test@example.com");
        sampleUser.setUserRole(UserRolesEnum.USER);
        sampleUser.setPassword("password");
    }

    @BeforeEach
    public void setUpAdmins() {
        adminUser = new AppUser();
        adminUser.setUserRole(UserRolesEnum.ADMIN);
        adminUser.setEmail("admin@example.com");

        nonAdminUser = new AppUser();
        nonAdminUser.setUserRole(UserRolesEnum.USER);
        nonAdminUser.setEmail("user@example.com");
    }

    @Test
    void testGetUserByEmail() {
        when(userRepository.findByEmail(sampleUser.getEmail())).thenReturn(Optional.of(sampleUser));

        AppUser result = appUserService.getUserByEmail(sampleUser.getEmail());
        assertEquals(sampleUser, result);
        verify(userRepository).findByEmail(sampleUser.getEmail());
    }


    @Test
    void testUserNameIsTaken() {
        when(userRepository.findUserByName(sampleUser.getName())).thenReturn(Optional.of(sampleUser));

        AppUser result = appUserService.userNameIsTaken(sampleUser.getName());
        assertEquals(sampleUser, result);
        verify(userRepository).findUserByName(sampleUser.getName());
    }

    @Test
    void testEmailIsTaken() {
        when(userRepository.findByEmail(sampleUser.getEmail())).thenReturn(Optional.of(sampleUser));

        AppUser result = appUserService.emailIsTaken(sampleUser.getEmail());
        assertEquals(sampleUser, result);
        verify(userRepository).findByEmail(sampleUser.getEmail());
    }


    @Test
    void testInitUsersDB() {
        when(userRepository.count()).thenReturn(0L);
        when(passwordEncoder.encode(jwtKeyProps.getAdminPassword())).thenReturn("encodedAdminPassword");
        when(userRepository.save(any(AppUser.class))).thenReturn(sampleUser);

        appUserService.initUsersDB();
        verify(userRepository, times(3)).save(any(AppUser.class));
    }

    @Test
    public void testIsAdminForAdminUser() {
        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(adminUser));

        boolean isAdmin = appUserService.isAdmin("admin@example.com");

        assertTrue(isAdmin, "Expected to be an admin");
    }

    @Test
    public void testIsAdminForNonAdminUser() {
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(nonAdminUser));

        boolean isAdmin = appUserService.isAdmin("user@example.com");

        assertFalse(isAdmin, "Expected not to be an admin");
    }

}
