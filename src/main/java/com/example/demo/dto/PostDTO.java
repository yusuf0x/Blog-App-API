package com.example.demo.dto;

import com.example.demo.models.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String postImage;
    private UserDTO user;
    private CategoryDTO category;
//    @JsonIgnore
    private Set<CommentDTO> comments = new HashSet<>();

    public static Post toEntity(PostDTO postDTO){
        if(postDTO == null){
            return null;
        }
        Post post1 = new Post();
        post1.setId(postDTO.getId());
        post1.setTitle(postDTO.getTitle());
        post1.setContent(postDTO.getContent());
        post1.setPostImage(postDTO.getPostImage());
        post1.setUser(UserDTO.toEntity(postDTO.user));
        post1.setCategory(CategoryDTO.toEntity(postDTO.category));
        post1.setComments(postDTO.comments.stream().map(
                CommentDTO::toEntity
        ).collect(Collectors.toSet()));
        return post1;
    }
    public static PostDTO fromEntity(Post post){
        if(post==null){
            return null;
        }
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .category(CategoryDTO.fromEntity(post.getCategory()))
                .user(UserDTO.fromEntity(post.getUser()))
                .postImage(post.getPostImage())
                .content(post.getContent())
                .comments(post.getComments().stream().map(CommentDTO::fromEntity).collect(Collectors.toSet()))
                .build();
    }
}
