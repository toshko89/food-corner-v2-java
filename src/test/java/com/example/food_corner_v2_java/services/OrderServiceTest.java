package com.example.food_corner_v2_java.services;

import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.Order;
import com.example.food_corner_v2_java.model.Product;
import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.model.dto.*;
import com.example.food_corner_v2_java.repository.OrderRepository;
import com.example.food_corner_v2_java.service.AppUserService;
import com.example.food_corner_v2_java.service.OrderService;
import com.example.food_corner_v2_java.service.ProductService;
import com.example.food_corner_v2_java.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AppUserService appUserService;

    @Mock
    private ProductService productService;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderService orderService;

    private AppUser appUser;
    private Restaurant restaurant;
    private Product product;
    private Order order;


    @BeforeEach
    public void setUp() {
        appUser = new AppUser();
        appUser.setId(1L);
        appUser.setEmail("test@example.com");

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        order = new Order();
        order.setId(1L);
        order.setBuyer(appUser);
        order.setRestaurant(List.of(restaurant));
        order.setProduct(List.of(product));
        order.setQuantity(2);
        order.setDate(LocalDate.now());
    }

    @Test
    public void testCreateOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setRestaurantId(1L);
        orderDTO.setProduct(product);
        orderDTO.getProduct().setName("Test Product");
        orderDTO.setQuantity(2);

        when(appUserService.getUserByEmail("test@example.com")).thenReturn(appUser);
        when(restaurantService.findById(1L)).thenReturn(restaurant);
        when(productService.findAllByName("Test Product")).thenReturn(List.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        orderService.createOrder(List.of(orderDTO), "test@example.com");

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testInitOrderDB() {
        List<Product> pizza = List.of(new Product());
        when(orderRepository.count()).thenReturn(0L);
        when(appUserService.getUserByEmail("todor@abv.bg")).thenReturn(appUser);
        when(restaurantService.findByName("Djikov")).thenReturn(restaurant);
        when(productService.findAllByName("Pizza")).thenReturn(pizza);

        orderService.initOrderDB();

        verify(orderRepository, times(3)).save(any(Order.class));
    }

    @Test
    void testGetOrders() {
        // Prepare test data
        String principal = "test@example.com";

        OrderViewDTO orderViewDTO = new OrderViewDTO()
                .setId(1L)
                .setDate(LocalDate.now())
                .setBuyer(new UserDTO())
                .setRestaurant(new RestaurantOrderDTO())
                .setQuantity(2)
                .setProduct(new ProductDTO());

        when(appUserService.getUserByEmail(principal)).thenReturn(appUser);
        when(orderRepository.findAllByBuyerId(appUser.getId())).thenReturn(Stream.of(order).collect(Collectors.toList()));
        when(modelMapper.map(order.getBuyer(), UserDTO.class)).thenReturn(orderViewDTO.getBuyer());
        when(modelMapper.map(order.getRestaurant().get(0), RestaurantOrderDTO.class)).thenReturn(orderViewDTO.getRestaurant());
        when(modelMapper.map(order.getProduct().get(0), ProductDTO.class)).thenReturn(orderViewDTO.getProduct());

        List<OrderViewDTO> result = orderService.getOrders(principal);

        assertEquals(1, result.size());
        assertEquals(orderViewDTO.getId(), result.get(0).getId());
        assertEquals(orderViewDTO.getDate(), result.get(0).getDate());
        assertEquals(orderViewDTO.getBuyer(), result.get(0).getBuyer());
        assertEquals(orderViewDTO.getRestaurant(), result.get(0).getRestaurant());
        assertEquals(orderViewDTO.getQuantity(), result.get(0).getQuantity());
        assertEquals(orderViewDTO.getProduct(), result.get(0).getProduct());
    }
}
