package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.dto.RegisterDTO;
import com.example.food_corner_v2_java.model.enums.UserRolesEnum;
import com.example.food_corner_v2_java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Autowired
    public AppUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AppUser getUserByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No such user!" + email));
    }

    public String register(RegisterDTO registerDTO) {
        AppUser user = new AppUser()
                .setUserRole(UserRolesEnum.USER)
                .setCity(null)
                .setEmail(registerDTO.getEmail())
                .setName(null)
                .setCity(null)
                .setAddress(null)
                .setPhone(null)
                .setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        this.userRepository.save(user);

        return jwtService.generateToken(user);
    }


    public AppUser userNameIsTaken(String name) {
        return this.userRepository.findUserByName(name).orElse(null);
    }

    public AppUser emailIsTaken(String email) {
        return this.userRepository.findByEmail(email).orElse(null);
    }

    public void initUsersDB() {
        if (this.userRepository.count() == 0) {
            AppUser appUser = new AppUser()
                    .setUserRole(UserRolesEnum.ADMIN)
                    .setCity("Sofia")
                    .setName("Todor Petkov")
                    .setEmail("todor@abv.bg")
                    .setPassword("123123")
                    .setPhone("088898989")
                    .setAddress("Geo Milev");
            this.userRepository.save(appUser);
        }
    }

}
