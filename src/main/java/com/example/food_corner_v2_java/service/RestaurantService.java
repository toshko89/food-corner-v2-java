package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.model.Comment;
import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.model.User;
import com.example.food_corner_v2_java.repository.RestaurantRepository;
import com.example.food_corner_v2_java.utils.CloudinaryImage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserService userService;
    private final CommentService commentService;

    public RestaurantService(RestaurantRepository restaurantRepository, UserService userService, CommentService commentService) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.commentService = commentService;
    }

//    public void initRestaurantDB() {
//        if (this.restaurantRepository.count() == 0) {
//            User userByEmail = this.userService.getUserByEmail("todor@abv.bg");
////            Set<Comment> allComments = this.commentService.findAllByRestaurantId(1L);
//            Restaurant restaurant = new Restaurant()
//                    .setAddress("Geo Milev")
//                    .setCity("Sofia")
//                    .setCategorie("Pizza")
//                    .setName("Djikov")
//                    .setImageUrl(new CloudinaryImage().setUrl("https://res.cloudinary.com/dl72c1rli/image/upload/v1649444689/yuwqnsjlbfjcl1k353e3.jpg"))
//                    .setRatingsCount(10)
//                    .setRating(5)
//                    .setWorkingHours("10:00-20:00");
////                    .setComments()
////                    .setOwner(userByEmail);
//
//            this.restaurantRepository.save(restaurant);
//        }
//    }

    public Restaurant findByName(String name) {
        return this.restaurantRepository.findByName(name);

    }

    @Transactional
    public List<Restaurant> findAll() {
        List<Restaurant> all = this.restaurantRepository.findAll();
        System.out.println();

        return all;
    }
}
