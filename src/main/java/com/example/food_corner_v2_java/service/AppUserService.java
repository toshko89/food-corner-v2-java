package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.auth.JwtService;
import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.dto.UserDTO;
import org.modelmapper.ModelMapper;
import com.example.food_corner_v2_java.model.dto.LoginDTO;
import com.example.food_corner_v2_java.model.dto.RegisterDTO;
import com.example.food_corner_v2_java.model.enums.UserRolesEnum;
import com.example.food_corner_v2_java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;


    @Autowired
    public AppUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    public AppUser getUserByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No such user! " + email));
    }

    public Map<String, Object> register(RegisterDTO registerDTO) {
        AppUser user = new AppUser()
                .setUserRole(UserRolesEnum.USER)
                .setEmail(registerDTO.getEmail())
                .setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        this.userRepository.save(user);

        UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);

        String token = jwtService.generateToken(user);

        return Map.of("token", token, "user", userDTO);
    }

    public Map<String, Object> login(LoginDTO loginDTO) {
        AppUser user = this.userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("No such user! " + loginDTO.getEmail()));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password!");
        }

        UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);

        String token = jwtService.generateToken(user);

        return Map.of("token", token, "user", userDTO);
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
