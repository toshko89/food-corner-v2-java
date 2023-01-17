package com.example.food_corner_v2_java.model;

import com.example.food_corner_v2_java.utils.CloudinaryImage;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private String categorie;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String workingHours;

    @ManyToOne
    private User owner;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToMany
    private Set<Products> products = new HashSet<>();

    private Long rating;

    private Long ratingsCount;

    public Restaurant() {
    }

    public Long getId() {
        return id;
    }

    public Restaurant setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Restaurant setName(String name) {
        this.name = name;
        return this;
    }

    public String getCategorie() {
        return categorie;
    }

    public Restaurant setCategorie(String categorie) {
        this.categorie = categorie;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Restaurant setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Restaurant setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public Restaurant setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
        return this;
    }

    public User getOwner() {
        return owner;
    }

    public Restaurant setOwner(User owner) {
        this.owner = owner;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Restaurant setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Set<Products> getProducts() {
        return products;
    }

    public Restaurant setProducts(Set<Products> products) {
        this.products = products;
        return this;
    }

    public Long getRating() {
        return rating;
    }

    public Restaurant setRating(Long rating) {
        this.rating = rating;
        return this;
    }

    public Long getRatingsCount() {
        return ratingsCount;
    }

    public Restaurant setRatingsCount(Long ratingsCount) {
        this.ratingsCount = ratingsCount;
        return this;
    }
}
