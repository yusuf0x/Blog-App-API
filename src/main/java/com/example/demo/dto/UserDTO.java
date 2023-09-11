package com.example.demo.dto;

import com.example.demo.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;

    @NotEmpty
    @Size(min = 3, message = "Username must be min of 4 characters")
    private String username;

    @Email(message = "Email address is not valid!!")
    private String email;

    @NotEmpty
    @Size(min = 4, max = 20, message = "Password must be min of 4 chars and max 20 chars!!")
    @JsonIgnore
    private String password;

    @NotEmpty
    private String about;
    @JsonIgnore
    private Set<CommentDTO> comments = new HashSet<>();

    public static User toEntity(UserDTO userDTO){
        if(userDTO==null){
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.username);
        user.setEmail(userDTO.getEmail());
        user.setAbout(userDTO.getAbout());
        user.setPassword(userDTO.getPassword());
        return user;
    }
    public static UserDTO fromEntity(User user){
        if(user == null){
            return null;
        }
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getName())
                .email(user.getEmail())
                .about(user.getAbout())
                .build();
    }
}
