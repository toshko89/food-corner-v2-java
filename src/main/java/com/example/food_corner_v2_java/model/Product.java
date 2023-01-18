package com.example.food_corner_v2_java.model;

import com.example.food_corner_v2_java.utils.CloudinaryImage;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    @Embedded
    private CloudinaryImage imageUrl;

    private String category;

    @ElementCollection
    private Set<String> ingredients = new HashSet<>();

    public Product() {
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public Product setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public CloudinaryImage getImageUrl() {
        return imageUrl;
    }

    public Product setImageUrl(CloudinaryImage imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Product setCategory(String category) {
        this.category = category;
        return this;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public Product setIngredients(Set<String> ingredients) {
        this.ingredients = ingredients;
        return this;
    }
}
