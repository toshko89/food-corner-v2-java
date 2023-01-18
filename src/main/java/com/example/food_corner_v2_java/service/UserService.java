package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.model.User;
import com.example.food_corner_v2_java.model.UserRoles;
import com.example.food_corner_v2_java.model.enums.UserRolesEnum;
import com.example.food_corner_v2_java.repository.UserRepository;
import com.example.food_corner_v2_java.repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("No such user!"));
    }

    public void initUsersDB() {
        if (this.userRepository.count() == 0) {
            UserRoles roleUser = this.userRolesRepository.findByUserRoles(UserRolesEnum.USER);
            UserRoles roleAdmin = this.userRolesRepository.findByUserRoles(UserRolesEnum.ADMIN);

            User user = new User()
                    .setUserRoles(Set.of(roleUser, roleAdmin))
                    .setName("Todor Petkov")
                    .setEmail("todor@abv.bg")
                    .setPassword("123123")
                    .setPhone("0889080808")
                    .setCity("Sofia")
                    .setAddress("Levski 21");

            this.userRepository.save(user);
        }
    }
}
