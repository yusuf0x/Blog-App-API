package com.example.demo.services;

import com.example.demo.dto.CommentDTO;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO, Long postId, Long userId);
    void deleteComment(Long commentId);
}
