package com.example.food_corner_v2_java.repository;

import com.example.food_corner_v2_java.model.UserRoles;
import com.example.food_corner_v2_java.model.enums.UserRolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles,Long> {
    UserRoles findByUserRoles(UserRolesEnum userRolesEnum);
}
