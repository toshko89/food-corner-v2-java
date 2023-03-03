package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.model.dto.CommentDTO;
import com.example.food_corner_v2_java.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/food-corner")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/restaurants/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getRestaurantComments(@PathVariable Long id) {
        try {
            List<CommentDTO> comments = this.commentService.findAllByRestaurantId(id);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/restaurants/{id}/comments-create")
    public ResponseEntity<String> createComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO, Principal principal) {
        try {
            this.commentService.createComment(id, commentDTO,principal.getName());
            return ResponseEntity.ok("Comment created");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/restaurants/comments-delete/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, Principal principal) {
        try {
            if(principal == null) return ResponseEntity.badRequest().body("You are not logged in");
            this.commentService.deleteComment(id, principal.getName());
            return ResponseEntity.ok("Comment deleted");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
