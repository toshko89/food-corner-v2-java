package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.model.dto.OrderDTO;
import com.example.food_corner_v2_java.model.dto.OrderViewDTO;
import com.example.food_corner_v2_java.service.OrderService;
import com.example.food_corner_v2_java.web.OrderContoller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class OrderContollerTest {

    @InjectMocks
    private OrderContoller orderContoller;

    @Mock
    private OrderService orderService;

    @Mock
    private Principal principal;

    @Test
    public void testCreateOrder() {
        OrderDTO orderDTO = new OrderDTO().setRestaurantId(1L).setQuantity(2);
        List<OrderDTO> orderDTOList = Collections.singletonList(orderDTO);
        when(principal.getName()).thenReturn("user1");

        ResponseEntity<String> responseEntity = orderContoller.createOrder(orderDTOList, principal);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Order created", responseEntity.getBody());
    }

    @Test
    public void testGetOrders() {
        OrderViewDTO orderViewDTO = new OrderViewDTO().setId(1L).setQuantity(2).setDate(LocalDate.now());
        List<OrderViewDTO> orderViewDTOList = Collections.singletonList(orderViewDTO);
        when(principal.getName()).thenReturn("user1");
        when(orderService.getOrders(principal.getName())).thenReturn(orderViewDTOList);

        ResponseEntity<List<OrderViewDTO>> responseEntity = orderContoller.getOrders(principal);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderViewDTOList, responseEntity.getBody());
    }

}
