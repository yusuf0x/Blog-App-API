package com.example.demo.services;

import com.example.demo.dto.PostDTO;
import com.example.demo.payload.response.PostResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface PostService {
    PostDTO createPost(PostDTO postDto, Long userId, Long categoryId);
    PostDTO updatePost(Long postId, PostDTO postDto);
    void deletePost(Long postID);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    PostDTO getPostById(Long postId);
    List<PostDTO> getPostsByCategory(Long categoryId);
    List<PostDTO> getPostsByUser(Long userId);
    // search posts
    List<PostDTO> searchPosts(String keyword);
}
