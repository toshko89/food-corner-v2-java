package com.example.food_corner_v2_java.init;

import com.example.food_corner_v2_java.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final ProductService productService;
    private final OrderService orderService;
    private final CommentService commentService;

    public DBInit(UserRoleService userRoleService, UserService userService, RestaurantService restaurantService, ProductService productService, OrderService orderService, CommentService commentService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.productService = productService;
        this.orderService = orderService;
        this.commentService = commentService;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.userRoleService.initUserRolesDB();
//        this.userService.initUsersDB();
//        this.restaurantService.initRestaurantDB();
//        this.productService.initProductDB();
//        this.orderService.initOrderDB();
//        this.commentService.initCommentDB();
    }
}
