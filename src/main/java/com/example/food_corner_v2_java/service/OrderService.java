package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.model.Order;
import com.example.food_corner_v2_java.model.Product;
import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.model.User;
import com.example.food_corner_v2_java.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final RestaurantService restaurantService;

    public OrderService(OrderRepository orderRepository, UserService userService, ProductService productService, RestaurantService restaurantService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
        this.restaurantService = restaurantService;
    }

    public void initOrderDB() {
        if (this.orderRepository.count() == 0) {
            User user = this.userService.getUserByEmail("todor@abv.bg");
            Restaurant restaurant = this.restaurantService.findByName("Djikov");
            List<Product> pizza = this.productService.findAllByName("Pizza");
            Order order = new Order()
                    .setBuyer(user)
                    .setRestaurant(List.of(restaurant))
                    .setProduct(pizza);
            this.orderRepository.save(order);

            Order order2 = new Order()
                    .setBuyer(user)
                    .setRestaurant(List.of(restaurant))
                    .setProduct(pizza);
            this.orderRepository.save(order2);

            Order order3 = new Order()
                    .setBuyer(user)
                    .setRestaurant(List.of(restaurant))
                    .setProduct(pizza);
            this.orderRepository.save(order3);
        }
    }
}
