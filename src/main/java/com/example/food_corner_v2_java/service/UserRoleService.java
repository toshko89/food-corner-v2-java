package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.model.UserRoles;
import com.example.food_corner_v2_java.model.enums.UserRolesEnum;
import com.example.food_corner_v2_java.repository.UserRolesRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserRoleService {
    private final UserRolesRepository userRoleRepository;

    public UserRoleService(UserRolesRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRoles getRole(UserRolesEnum userRolesEnum){
        return this.userRoleRepository.findByUserRoles(userRolesEnum);
    }

    public void initUserRolesDB(){
        if(this.userRoleRepository.count() == 0){
            Set<UserRoles> userRoles = Set.of(
                    new UserRoles().setUserRoles(UserRolesEnum.USER),
                    new UserRoles().setUserRoles(UserRolesEnum.ADMIN)
            );

            this.userRoleRepository.saveAll(userRoles);
        }
    }
}
