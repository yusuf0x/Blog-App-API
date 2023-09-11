package com.example.demo.services.impl;

import com.example.demo.dto.UserDTO;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.User;
import com.example.demo.payload.response.UserResponse;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserDTO.toEntity(userDTO);
        User saved = userRepository.save(user);
        return UserDTO.fromEntity(saved);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", " Id ", id));
        user.setName(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());
        User updatedUser = userRepository.save(user);
        BeanUtils.copyProperties(updatedUser, userDTO);
        return null;
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", " Id ", id));
        return UserDTO.fromEntity(user);
    }

    @Override
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase(sortDir)) ?
                sort = Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<User> users = userRepository.findAll(pageRequest);
        List<UserDTO> userDtoList = users
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(userDtoList);
        userResponse.setPageNumber(users.getNumber());
        userResponse.setPageSize(users.getSize());
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setTotalPages(users.getTotalPages());
        userResponse.setLastPage(users.isLast());
        return userResponse;
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", "Id", id));
        userRepository.delete(user);
    }
}
