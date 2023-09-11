package com.example.demo.services;

import com.example.demo.dto.UserDTO;
import com.example.demo.payload.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDTO createUser(UserDTO user);
    UserDTO updateUser(UserDTO user,Long id);
    UserDTO getUserById(Long id);
    UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    void deleteUser(Long id);
}
