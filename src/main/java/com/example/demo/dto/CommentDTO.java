package com.example.demo.dto;


import com.example.demo.models.Comment;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    private String comment;
    private PostDTO postDTO;
    private UserDTO userDTO;

    public static CommentDTO fromEntity(Comment comment){
        if(comment==null){
            return null;
        }
        return CommentDTO.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .postDTO(PostDTO.builder().id(comment.getPost().getId()).build())
                .userDTO(UserDTO.builder().id(comment.getUser().getId()).build())
                .build();
    }
    public static Comment toEntity(CommentDTO commentDTO){
        if(commentDTO == null){
            return null;
        }
        Comment comment1 = new Comment();
        comment1.setId(commentDTO.getId());
        comment1.setComment(commentDTO.getComment());
        comment1.setPost(Post.builder().id(commentDTO.postDTO.getId()).build());
        comment1.setUser(User.builder().id(commentDTO.userDTO.getId()).build());
        return comment1;
    }

}
