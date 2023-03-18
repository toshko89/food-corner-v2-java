package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.auth.JwtKeyProps;
import com.example.food_corner_v2_java.auth.JwtService;
import com.example.food_corner_v2_java.errors.AppException;
import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.dto.ChangeUserDataDTO;
import com.example.food_corner_v2_java.model.dto.UserDTO;
import org.modelmapper.ModelMapper;
import com.example.food_corner_v2_java.model.dto.LoginDTO;
import com.example.food_corner_v2_java.model.dto.RegisterDTO;
import com.example.food_corner_v2_java.model.enums.UserRolesEnum;
import com.example.food_corner_v2_java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final JwtKeyProps jwtKeyProps;


    @Autowired
    public AppUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, ModelMapper modelMapper, JwtKeyProps jwtKeyProps) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
        this.jwtKeyProps = jwtKeyProps;
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

    public Map<String, Object> editUser(Long id, ChangeUserDataDTO changeUserDataDTO) {
        AppUser user = this.userRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST,"No such user! " + id));

        user.setName(changeUserDataDTO.getName())
                .setPhone(changeUserDataDTO.getPhone())
                .setAddress(changeUserDataDTO.getAddress())
                .setCity(changeUserDataDTO.getCity());

        this.userRepository.save(user);

        String token = jwtService.generateToken(user);

        UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);

        return Map.of("token", token, "user", userDTO);
    }

    public void initUsersDB() {
        if (this.userRepository.count() == 0) {
            AppUser appUser = new AppUser()
                    .setUserRole(UserRolesEnum.ADMIN)
                    .setCity("Sofia")
                    .setName("Todor Petkov")
                    .setEmail("todor@abv.bg")
                    .setPassword(passwordEncoder.encode(jwtKeyProps.getAdminPassword()))
                    .setPhone("088898989")
                    .setAddress("Geo Milev");
            this.userRepository.save(appUser);

            AppUser appUser2 = new AppUser()
                    .setUserRole(UserRolesEnum.ADMIN)
                    .setCity("Sofia")
                    .setName("Gosho Petkov")
                    .setEmail("goshko@abv.bg")
                    .setPassword(passwordEncoder.encode(jwtKeyProps.getAdminPassword()))
                    .setPhone("088754545")
                    .setAddress("Lulin 3");
            this.userRepository.save(appUser2);

            AppUser appUser3 = new AppUser()
                    .setUserRole(UserRolesEnum.ADMIN)
                    .setCity("Sofia")
                    .setName("Petko Petkov")
                    .setEmail("petko@abv.bg")
                    .setPassword(passwordEncoder.encode(jwtKeyProps.getAdminPassword()))
                    .setPhone("088754545")
                    .setAddress("Lozenec");
            this.userRepository.save(appUser3);
        }
    }

    public boolean isAdmin(String principalName) {
        AppUser user = this.userRepository.findByEmail(principalName)
                .orElseThrow(() -> new UsernameNotFoundException("No such user! " + principalName));

        return user.getUserRole().equals(UserRolesEnum.ADMIN);
    }
}
