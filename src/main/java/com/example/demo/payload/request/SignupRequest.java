package com.example.demo.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class SignupRequest {
    @NotBlank
    @Size(min = 3, message = "Username must be min of 4 characters")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email(message = "Email address is not valid!!")
    private String email;
    

    @NotBlank
    @Size(min = 4, max = 20, message = "Password must be min of 4 chars and max 20 chars!!")
    private String password;

    @NotBlank
    private String about;

}
