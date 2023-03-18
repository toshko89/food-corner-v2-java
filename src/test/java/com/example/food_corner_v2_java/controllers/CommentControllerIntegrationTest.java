package com.example.food_corner_v2_java.controllers;

import com.example.food_corner_v2_java.model.dto.CommentDTO;
import com.example.food_corner_v2_java.service.CommentService;
import com.example.food_corner_v2_java.web.CommentController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    public void setUp() {
        commentController = new CommentController(commentService);
    }

    @Test
    public void testGetRestaurantComments() throws Exception {
        // Set up mock response
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("Test comment");
        when(commentService.findAllByRestaurantId(anyLong())).thenReturn(Collections.singletonList(commentDTO));

        // Perform GET request and assert response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/food-corner/restaurants/1/comments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}
