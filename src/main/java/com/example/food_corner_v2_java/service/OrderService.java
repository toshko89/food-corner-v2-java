package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.Order;
import com.example.food_corner_v2_java.model.Product;
import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.model.dto.*;
import com.example.food_corner_v2_java.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final AppUserService appUserService;
    private final ProductService productService;
    private final RestaurantService restaurantService;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, AppUserService appUserService, ProductService productService, RestaurantService restaurantService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.appUserService = appUserService;
        this.productService = productService;
        this.restaurantService = restaurantService;
        this.modelMapper = modelMapper;
    }

    public void createOrder(List<OrderDTO> orderDTO, String principal) {
        AppUser appUser = this.appUserService.getUserByEmail(principal);
        for (OrderDTO or : orderDTO) {
            Order newOrder = new Order();
            Restaurant restaurant = this.restaurantService.findById(or.getRestaurantId());
            List<Product> product = this.productService.findAllByName(or.getProduct().getName());
            newOrder.setBuyer(appUser)
                    .setRestaurant(List.of(restaurant))
                    .setProduct(product)
                    .setQuantity(or.getQuantity());
            this.orderRepository.save(newOrder);
        }
    }

    @Transactional
    public List<OrderViewDTO> getOrders(String principal) {

        AppUser appUser = this.appUserService.getUserByEmail(principal);

        return this.orderRepository.findAllByBuyerId(appUser.getId())
                .stream().map(order -> {
                    return new OrderViewDTO()
                            .setId(order.getId())
                            .setDate(order.getDate())
                            .setBuyer(modelMapper.map(order.getBuyer(), UserDTO.class))
                            .setRestaurant(modelMapper.map(order.getRestaurant().get(0), RestaurantOrderDTO.class))
                            .setQuantity(order.getQuantity())
                            .setProduct(modelMapper.map(order.getProduct().get(0), ProductDTO.class));
                }).toList();
    }

    @Scheduled(cron = "0 0 1 * * ?") // Every day at 1:00 AM
    public void deleteOrdersOlderThanOneDay() {
        LocalDate dayAgo = LocalDate.now().minusDays(1);
        this.orderRepository.deleteAllByDateBefore(dayAgo);
    }

    public void initOrderDB() {
        if (this.orderRepository.count() == 0) {
            AppUser appUser = this.appUserService.getUserByEmail("todor@abv.bg");
            Restaurant restaurant = this.restaurantService.findByName("Djikov");
            List<Product> pizza = this.productService.findAllByName("Pizza");
            Order order = new Order()
                    .setBuyer(appUser)
                    .setRestaurant(List.of(restaurant))
                    .setProduct(pizza).setQuantity(2);
            this.orderRepository.save(order);

            Order order2 = new Order()
                    .setBuyer(appUser)
                    .setRestaurant(List.of(restaurant))
                    .setProduct(pizza).setQuantity(1);
            this.orderRepository.save(order2);

            Order order3 = new Order()
                    .setBuyer(appUser)
                    .setRestaurant(List.of(restaurant))
                    .setProduct(pizza).setQuantity(3);
            this.orderRepository.save(order3);
        }
    }
}

