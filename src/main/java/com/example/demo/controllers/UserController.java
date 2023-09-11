package com.example.demo.controllers;

import com.example.demo.config.AppConstants;
import com.example.demo.dto.UserDTO;
import com.example.demo.payload.response.UserResponse;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<UserResponse> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        return ResponseEntity.ok(userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir));
    }

}
