package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.enums.UserRolesEnum;
import com.example.food_corner_v2_java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    private final UserRepository userRepository;


    @Autowired
    public AppUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("No such user!"));
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
