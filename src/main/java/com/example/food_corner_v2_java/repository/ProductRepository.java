package com.example.food_corner_v2_java.repository;

import com.example.food_corner_v2_java.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByName(String name);

    List<Product> findAllByName(String name);
}

