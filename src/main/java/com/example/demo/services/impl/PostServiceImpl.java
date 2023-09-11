package com.example.demo.services.impl;

import com.example.demo.dto.PostDTO;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Category;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.payload.response.PostResponse;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", " Id ", userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", " Id ", categoryId));
        Post post = PostDTO.toEntity(postDTO);
        post.setUser(user);
        post.setCategory(category);
        Post saved = postRepository.save(post);
        return PostDTO.fromEntity(saved);
    }

    @Override
    public PostDTO updatePost(Long postId, PostDTO postDto) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", " Id ", postId));
        BeanUtils.copyProperties(postDto,post);
        post.setId(postId);
        Post updated = postRepository.save(post);
        return PostDTO.fromEntity(updated);
    }

    @Override
    public void deletePost(Long postID) {
        Post post = postRepository.findById(postID).orElseThrow(() ->
                new ResourceNotFoundException("Post", " Id ", postID));
        postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase(sortDir)) ?
                sort = Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest p = PageRequest.of(pageNumber, pageSize,sort);

        Page<Post> posts = postRepository.findAll(p);

        List<PostDTO> postDtoList = posts
                .stream()
                .map(PostDTO::fromEntity)
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLastPage(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", " Id ", postId));
        return PostDTO.fromEntity(post);
    }

    @Override
    public List<PostDTO> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", " Id ", categoryId));
        List<Post> posts = postRepository.findByCategory(category);
        List<PostDTO> postDTOList = posts.stream().map(
                PostDTO::fromEntity
        ).toList();
        return postDTOList;
    }

    @Override
    public List<PostDTO> getPostsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", " Id ", userId));
        List<Post> posts = postRepository.findByUser(user);
        List<PostDTO> postDtoList = posts
                .stream()
                .map(PostDTO::fromEntity)
                .collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        List<Post> posts = postRepository.searchByTitle(keyword);
        List<PostDTO> postDtoList = posts
                .stream()
                .map(PostDTO::fromEntity)
                .collect(Collectors.toList());
        return postDtoList;
    }
}
