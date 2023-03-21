package com.example.food_corner_v2_java.services;

import com.example.food_corner_v2_java.errors.AppException;
import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.model.Comment;
import com.example.food_corner_v2_java.model.Restaurant;
import com.example.food_corner_v2_java.model.dto.CommentDTO;
import com.example.food_corner_v2_java.repository.CommentRepository;
import com.example.food_corner_v2_java.service.AppUserService;
import com.example.food_corner_v2_java.service.CommentService;
import com.example.food_corner_v2_java.service.RestaurantService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private Comment comment;
    private Restaurant restaurant;
    private AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser = new AppUser().setEmail("test@abv.bg");
        restaurant = new Restaurant().setName("Test").setId(1L).setRating(5).setRatingsCount(1);
        comment = new Comment()
                .setTitle("Otlichen 6")
                .setComment("Mnogo hubavo mqsto")
                .setDate(LocalDate.now())
                .setOwner(appUser)
                .setRating(5)
                .setRestaurants(restaurant);
    }

    @AfterEach
    void tearDown() {
        this.commentRepository.delete(comment);
        this.restaurantService.deleteRestaurant(restaurant.getId());
        comment = null;
        restaurant = null;
        appUser = null;
    }

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
    void createCommentCalculatesNewRatingAndSavesComment() {
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

    @Test
    void deleteCommentSuccess() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(restaurantService.findById(1L)).thenReturn(restaurant);
        when(appUserService.getUserByEmail(appUser.getEmail())).thenReturn(appUser);
        commentService.deleteComment(1L, appUser.getEmail());
        verify(commentRepository).delete(comment);
        verify(restaurantService).save(restaurant);
    }

    @Test
    void deleteCommentCommentNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> commentService.deleteComment(1L, appUser.getEmail()));
    }

    @Test
    void updateCommentCommentNotFound() {
        CommentDTO commentDTO = new CommentDTO()
                .setTitle("New Title")
                .setComment("New Comment")
                .setRating(4);

        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> commentService.updateComment(1L, commentDTO));
    }

    @Test
    void testInitCommentDBDoesNotSaveCommentWhenRepositoryIsNotEmpty() {
        when(commentRepository.count()).thenReturn(1L);
        commentService.initCommentDB();
        verify(commentRepository, never()).save(any());
    }

    @Test
    void testUpdateCommentThrowsExceptionWhenCommentDoesNotExist() {
        Long id = 1L;
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setTitle("New title");
        commentDTO.setComment("New comment");
        commentDTO.setRating(4);
        when(commentRepository.findById(id)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> commentService.updateComment(id, commentDTO));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Comment not found", exception.getMessage());
    }

    @Test
    void testInitCommentDBAddsCommentWhenRepositoryIsEmpty() {
        // Arrange
        when(commentRepository.count()).thenReturn(0L);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        when(restaurantService.findById(1L)).thenReturn(restaurant);
        AppUser appUser = new AppUser();
        appUser.setEmail("todor@abv.bg");
        when(appUserService.getUserByEmail("todor@abv.bg")).thenReturn(appUser);

        commentService.initCommentDB();

        ArgumentCaptor<Comment> argumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository, times(1)).save(argumentCaptor.capture());
        Comment comment = argumentCaptor.getValue();
        assertEquals("Otlichen 6", comment.getTitle());
        assertEquals("Mnogo hubavo mqsto", comment.getComment());
        assertNotNull(comment.getDate());
        assertEquals(appUser, comment.getOwner());
        assertEquals(5, comment.getRating());
        assertEquals(restaurant, comment.getRestaurants());
    }

}
