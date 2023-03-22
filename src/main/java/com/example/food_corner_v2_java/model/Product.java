package com.example.food_corner_v2_java.model;

import com.example.food_corner_v2_java.utils.CloudinaryImage;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.*;

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
    private String category;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    @Embedded
    private CloudinaryImage imageUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> ingredients = new ArrayList<>();


    public Product() {
    }


    public Long getId() {
        return id;
    }

    public Product setId(Long id) {
        this.id = id;
        return this;
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

    public List<String> getIngredients() {
        return ingredients;
    }

    public Product setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id) && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
