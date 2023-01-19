package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.model.User;
import com.example.food_corner_v2_java.model.enums.UserRolesEnum;
import com.example.food_corner_v2_java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("No such user!"));
    }

    public void initUsersDB() {
        if (this.userRepository.count() == 0) {
            User user = new User()
                    .setUserRole(UserRolesEnum.ADMIN)
                    .setCity("Sofia")
                    .setName("Todor Petkov")
                    .setEmail("todor@abv.bg")
                    .setPassword("123123")
                    .setPhone("088898989")
                    .setAddress("Geo Milev");
            this.userRepository.save(user);
        }
    }
}
