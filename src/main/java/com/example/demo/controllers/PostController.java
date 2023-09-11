package com.example.demo.controllers;

import com.example.demo.config.AppConstants;
import com.example.demo.dto.PostDTO;
import com.example.demo.exceptions.ApiResponse;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payload.response.PostResponse;
import com.example.demo.services.FileService;
import com.example.demo.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping(value = "/users/{userId}/categories/{categoryId}/posts",consumes = {"multipart/form-data"})
    public ResponseEntity<PostDTO> createPost(
            @RequestPart  MultipartFile file,
            @RequestPart PostDTO postDto,
            @PathVariable Long userId,
            @PathVariable Long categoryId
        ) throws IOException{
        String fileName = fileService.uploadImage(path, file);
        postDto.setPostImage(fileName);
        PostDTO createdPost = postService.createPost(postDto, userId, categoryId);
        System.out.println(fileName);
        return new ResponseEntity<PostDTO>(createdPost, HttpStatus.CREATED);
    }
    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Long userId) {
        List<PostDTO> posts = postService.getPostsByUser(userId);
        if (posts.isEmpty()) {
            throw new ResourceNotFoundException("Posts", " UserId: ", userId);
        }
        return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
    }
    @GetMapping("/categories/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Long categoryId) {
        List<PostDTO> posts = postService.getPostsByCategory(categoryId);
        if (posts.isEmpty()) {
            throw new ResourceNotFoundException("Posts", " CategoryId: ", categoryId);
        }
        return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
    }
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        PostResponse postResponse = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getPostsById(@PathVariable Long postId) {
        PostDTO postDto = postService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ApiResponse("Post is successfully deleted!", true);
    }

    @PutMapping(value = "/posts/{postId}",consumes = {"multipart/form-data"})
    public ResponseEntity<PostDTO> updatePost(
            @PathVariable Long postId,
            @RequestPart  MultipartFile file,
            @RequestPart PostDTO postDto
    ) throws IOException {
        String fileName = fileService.uploadImage(path, file);
        postDto.setPostImage(fileName);
        PostDTO updatedPost = postService.updatePost(postId, postDto);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDTO>> searchPostByTitle(
            @PathVariable("keyword") String keyword) {
        List<PostDTO> postDtoList = postService.searchPosts(keyword);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }
    @PostMapping("post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Long postId) throws IOException {

        PostDTO postDto = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        postDto.setPostImage(fileName);
        PostDTO updatedPost = postService.updatePost(postId, postDto);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    // method to serve files
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }



}
