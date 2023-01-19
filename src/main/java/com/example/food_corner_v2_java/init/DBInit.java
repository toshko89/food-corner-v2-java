package com.example.food_corner_v2_java.init;

import com.example.food_corner_v2_java.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class DBInit implements CommandLineRunner {

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final ProductService productService;
    private final OrderService orderService;
    private final CommentService commentService;

    @Autowired
    public DBInit(UserService userService, RestaurantService restaurantService, ProductService productService, OrderService orderService, CommentService commentService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.productService = productService;
        this.orderService = orderService;
        this.commentService = commentService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.initUsersDB();
        this.productService.initProductDB();
        this.restaurantService.initRestaurantDB();
        this.orderService.initOrderDB();
        this.commentService.initCommentDB();
    }
}
