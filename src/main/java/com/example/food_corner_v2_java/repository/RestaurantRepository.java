package com.example.food_corner_v2_java.repository;

import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    Restaurant findByName(String name);

    List<Restaurant> findAllByOwner(AppUser appUser);
}
