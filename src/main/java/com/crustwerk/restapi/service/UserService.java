package com.crustwerk.restapi.service;

import com.crustwerk.restapi.dto.UserDTO;
import com.crustwerk.restapi.mapper.UserMapper;
import com.crustwerk.restapi.model.User;
import com.crustwerk.restapi.repository.UserRepository; // supponendo che tu abbia un repository
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
        // Validazione semplice: password e conferma devono coincidere
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and Confirm Password do not match");
        }

        User user = userMapper.toModel(userDTO);
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPasswordHash(hashedPassword);
        user.setCreatedAt(java.time.LocalDate.now());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Altri metodi come update, delete, find possono seguire una logica simile
}
