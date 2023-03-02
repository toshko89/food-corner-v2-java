package com.example.food_corner_v2_java.model;

import com.example.food_corner_v2_java.utils.CloudinaryImage;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String workingHours;

    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser owner;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Embedded
    private CloudinaryImage imageUrl;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    private double rating;

    private Integer ratingsCount;

    public Restaurant() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Restaurant setName(String name) {
        this.name = name;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Restaurant setCategory(String category) {
        this.category = category;
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

    public AppUser getOwner() {
        return owner;
    }

    public Restaurant setOwner(AppUser owner) {
        this.owner = owner;
        return this;
    }

    public CloudinaryImage getImageUrl() {
        return imageUrl;
    }

    public Restaurant setImageUrl(CloudinaryImage imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Restaurant setProducts(Product products) {
        this.products.add(products);
        return this;
    }

    public double getRating() {
        return rating;
    }

    public Restaurant setRating(double rating) {
        this.rating = rating;
        return this;
    }

    public Integer getRatingsCount() {
        return ratingsCount;
    }

    public Restaurant setRatingsCount(Integer ratingsCount) {
        this.ratingsCount = ratingsCount;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return id.equals(that.id) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }
}
