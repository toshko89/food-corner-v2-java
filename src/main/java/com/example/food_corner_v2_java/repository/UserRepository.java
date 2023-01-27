package com.example.food_corner_v2_java.repository;

import com.example.food_corner_v2_java.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findByEmail(String email);
}
