package com.example.food_corner_v2_java.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser owner;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "DATETIME")
    @CreationTimestamp
    private LocalDate date;

    @ManyToOne
    private Restaurant restaurants;

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public Comment setId(Long id) {
        this.id = id;
        return this;
    }

    public AppUser getOwner() {
        return owner;
    }

    public Comment setOwner(AppUser owner) {
        this.owner = owner;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Comment setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public Comment setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Comment setTitle(String title) {
        this.title = title;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Comment setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Restaurant getRestaurants() {
        return restaurants;
    }

    public Comment setRestaurants(Restaurant restaurants) {
        this.restaurants = restaurants;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id) && owner.equals(comment.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner);
    }
}
