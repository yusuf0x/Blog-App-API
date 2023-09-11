package com.example.demo.services.impl;

import com.example.demo.dto.CommentDTO;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Comment;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(()->  new ResourceNotFoundException("Post", " Id ", postId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO,comment);
        comment.setUser(user);
        comment.setPost(post);
        Comment saved = commentRepository.save(comment);
        return CommentDTO.fromEntity(saved);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment"," Id ",commentId));
        commentRepository.delete(comment);
    }
}
