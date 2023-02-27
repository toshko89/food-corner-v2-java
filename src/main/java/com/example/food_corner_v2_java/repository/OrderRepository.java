package com.example.food_corner_v2_java.repository;


import com.example.food_corner_v2_java.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByBuyerId(Long id);
}

