package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.errors.AppException;
import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.Comment;
import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.model.dto.CommentDTO;
import com.example.food_corner_v2_java.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final RestaurantService restaurantService;
    private final AppUserService appUserService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, RestaurantService restaurantService, AppUserService appUserService, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.restaurantService = restaurantService;
        this.appUserService = appUserService;
        this.modelMapper = modelMapper;
    }

    public List<CommentDTO> findAllByRestaurantId(long id) {
        return this.commentRepository.findAllByRestaurantsId(id)
                .stream().map(comment -> this.modelMapper.map(comment, CommentDTO.class))
                .toList();
    }

    public Comment createComment(Long id, CommentDTO commentDTO, String principalName) {
        Restaurant restaurant = this.restaurantService.findById(id);
        restaurant.setRatingsCount(restaurant.getRatingsCount() + 1);
        restaurant.setRating(((restaurant.getRating() * restaurant.getRatingsCount()) + commentDTO.getRating()) / (restaurant.getRatingsCount() + 1));
        AppUser appUser = this.appUserService.getUserByEmail(principalName);
        Comment comment = new Comment()
                .setTitle(commentDTO.getTitle())
                .setComment(commentDTO.getComment())
                .setDate(LocalDate.now())
                .setOwner(appUser)
                .setRating(commentDTO.getRating())
                .setRestaurants(restaurant);
        Comment commentId = this.commentRepository.save(comment);
        this.restaurantService.save(restaurant);
        return commentId;
    }

    public boolean deleteComment(Long id, String principalName) {
        Comment comment = this.commentRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Comment not found"));
        Restaurant restaurant = comment.getRestaurants();
        restaurant.setRatingsCount(restaurant.getRatingsCount() - 1);
        restaurant.setRating(((restaurant.getRating() * restaurant.getRatingsCount()) - comment.getRating()) / (restaurant.getRatingsCount() - 1));
        this.commentRepository.delete(comment);
        this.restaurantService.save(restaurant);
        return true;
    }

    public List<CommentDTO> updateComment(Long id, CommentDTO commentDTO) {
        Comment comment = this.commentRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Comment not found"));
        Restaurant restaurant = comment.getRestaurants();
        restaurant.setRating(((restaurant.getRating() * restaurant.getRatingsCount()) - comment.getRating() + commentDTO.getRating()) / restaurant.getRatingsCount());
        comment.setTitle(commentDTO.getTitle())
                .setComment(commentDTO.getComment())
                .setRating(commentDTO.getRating());
        this.commentRepository.save(comment);
        this.restaurantService.save(restaurant);
        return findAllByRestaurantId(restaurant.getId());
    }

    public void initCommentDB() {
        if (this.commentRepository.count() == 0) {
            Restaurant restaurant = this.restaurantService.findById(Long.parseLong("1"));
            AppUser appUser = this.appUserService.getUserByEmail("todor@abv.bg");
            Comment comment = new Comment()
                    .setTitle("Otlichen 6")
                    .setComment("Mnogo hubavo mqsto")
                    .setDate(LocalDate.now())
                    .setOwner(appUser)
                    .setRating(5)
                    .setRestaurants(restaurant);
            this.commentRepository.save(comment);
        }
    }
}
