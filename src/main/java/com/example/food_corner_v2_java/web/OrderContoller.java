package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.model.dto.OrderDTO;
import com.example.food_corner_v2_java.model.dto.OrderViewDTO;
import com.example.food_corner_v2_java.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/food-corner")
public class OrderContoller {

    private final OrderService ordersService;

    public OrderContoller(OrderService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/orders/create")
    public ResponseEntity<String> createOrder(@RequestBody List<OrderDTO> orderDTO, Principal principal) {
        try {
            ordersService.createOrder(orderDTO, principal.getName());
            return ResponseEntity.ok("Order created");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderViewDTO>> getOrders(Principal principal) {
        try {
            List<OrderViewDTO> orders = ordersService.getOrders(principal.getName());
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
