package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.model.Comment;
import com.example.food_corner_v2_java.model.dto.CommentDTO;
import com.example.food_corner_v2_java.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    private CommentDTO commentDTO;
    private Comment comment;

    @BeforeEach
    public void setUp() {
        commentDTO = new CommentDTO(1L, "Test comment", "Test comment", LocalDate.now(), 5, null);
        comment = new Comment().setId(1L);
    }

    @AfterEach
    public void tearDown() {
        this.commentService.deleteComment(comment.getId(),"todor@abv.bg");
    }


    private Principal createTestPrincipal() {
        return new Principal() {
            @Override
            public String getName() {
                return "test@example.com";
            }
        };
    }

    @Test
    public void testGetRestaurantComments() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("Test comment");
        when(commentService.findAllByRestaurantId(anyLong())).thenReturn(Collections.singletonList(commentDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/food-corner/restaurants/1/comments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "todor@abv.bg", password = "testpassword", authorities = {"ROLE_ADMIN"})
    public void testCreateComment() throws Exception {
        when(commentService.createComment(anyLong(),any(), eq(createTestPrincipal().getName()))).thenReturn(comment);
        mockMvc.perform(post("/api/food-corner/restaurants/1/comments-create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDTO))
                        .principal(createTestPrincipal()))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment created"));
    }

}
