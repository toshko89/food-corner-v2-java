package com.example.food_corner_v2_java.services;

import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.Comment;
import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.model.dto.CommentDTO;
import com.example.food_corner_v2_java.repository.CommentRepository;
import com.example.food_corner_v2_java.service.AppUserService;
import com.example.food_corner_v2_java.service.CommentService;
import com.example.food_corner_v2_java.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private AppUserService appUserService;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void findAllByRestaurantId() {
        long restaurantId = 1L;
        Comment comment = new Comment(); // Initialize comment with desired values
        CommentDTO commentDTO = new CommentDTO(); // Initialize commentDTO with desired values

        when(commentRepository.findAllByRestaurantsId(restaurantId)).thenReturn(List.of(comment));
        when(modelMapper.map(comment, CommentDTO.class)).thenReturn(commentDTO);

        List<CommentDTO> result = commentService.findAllByRestaurantId(restaurantId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(commentDTO, result.get(0));
    }

    @Test
    void createComment_calculatesNewRatingAndSavesComment() {
        Long restaurantId = 1L;
        String principalName = "test@example.com";
        CommentDTO commentDTO = new CommentDTO().setComment("test comment").setRating(5);
        Restaurant restaurant = new Restaurant();
        restaurant.setRating(3.0);
        restaurant.setRatingsCount(2);
        AppUser appUser = new AppUser();
        appUser.setEmail(principalName);

        when(restaurantService.findById(restaurantId)).thenReturn(restaurant);
        when(appUserService.getUserByEmail(principalName)).thenReturn(appUser);
        when(commentRepository.save(any(Comment.class))).thenAnswer(i -> i.getArguments()[0]);

        commentService.createComment(restaurantId, commentDTO, principalName);

        verify(restaurantService, times(1)).findById(restaurantId);
        verify(appUserService, times(1)).getUserByEmail(principalName);
        verify(commentRepository, times(1)).save(any(Comment.class));
        verify(restaurantService, times(1)).save(restaurant);

        assertEquals(3, restaurant.getRatingsCount());
        assertEquals(3.5, restaurant.getRating(), 0.000001);
    }

}
