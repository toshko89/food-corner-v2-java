package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.model.Comment;
import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.model.User;
import com.example.food_corner_v2_java.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final RestaurantService restaurantService;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, RestaurantService restaurantService, UserService userService) {
        this.commentRepository = commentRepository;
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

//    public void initCommentDB() {
//        if (this.commentRepository.count() == 0) {
//            Restaurant restaurant = this.restaurantService.findByName("Djikov");
//            User user = this.userService.getUserByEmail("todor@abv.bg");
//            Comment comment = new Comment()
//                    .setOwner(user)
//                    .setComment("Mnogo hubavo mqsto")
//                    .setDate(LocalDate.now())
////                    .setRestaurants(Set.of(restaurant))
//                    .setRating(3);
//
//            this.commentRepository.save(comment);
//        }
//    }

//    public Set<Comment> findAllByRestaurantId(long id) {
//      return this.commentRepository.findAllByRestaurantId(id);
//    }
}
