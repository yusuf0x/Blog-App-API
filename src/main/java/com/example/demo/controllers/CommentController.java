package com.example.demo.controllers;

import com.example.demo.dto.CommentDTO;
import com.example.demo.exceptions.ApiResponse;
import com.example.demo.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/user/{userId}/post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(
            @RequestBody CommentDTO comment,
            @PathVariable("postId") Long postId,
            @PathVariable("userId") Long userId) {

        CommentDTO createdComment = commentService.createComment(comment, postId, userId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment is successfully deleted", true), HttpStatus.OK);
    }
}
