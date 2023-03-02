package com.example.food_corner_v2_java.repository;

import com.example.food_corner_v2_java.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllByRestaurantsId(Long id);
}

