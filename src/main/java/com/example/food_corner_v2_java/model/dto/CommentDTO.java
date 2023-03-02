package com.example.food_corner_v2_java.model.dto;

import java.time.LocalDate;

public class CommentDTO {
    private Long id;
    private String title;
    private String comment;
    private LocalDate date;
    private int rating;
    private UserDTO owner;

    public CommentDTO() {
    }

    public Long getId() {
        return id;
    }

    public CommentDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public CommentDTO setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public CommentDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public CommentDTO setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public CommentDTO setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public CommentDTO setOwner(UserDTO owner) {
        this.owner = owner;
        return this;
    }
}
