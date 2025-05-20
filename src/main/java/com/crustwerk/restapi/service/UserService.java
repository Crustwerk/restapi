package com.crustwerk.restapi.service;

import com.crustwerk.restapi.dto.UserDTO;
import com.crustwerk.restapi.mapper.UserMapper;
import com.crustwerk.restapi.model.User;
import com.crustwerk.restapi.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and Confirm Password do not match");
        }

        User user = userMapper.toModel(userDTO);
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        user.setCreatedAt(java.time.LocalDate.now());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Altri metodi come update, delete, etc
}
