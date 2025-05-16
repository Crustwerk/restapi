package com.crustwerk.restapi.service;

import com.crustwerk.restapi.dto.UserDTO;
import com.crustwerk.restapi.mapper.UserMapper;
import com.crustwerk.restapi.model.User;
import com.crustwerk.restapi.repository.UserRepository; // supponendo che tu abbia un repository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User createUser(UserDTO userDTO) {
        // Validazione semplice: password e conferma devono coincidere
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and Confirm Password do not match");
        }

        // Converti DTO in Model
        User user = userMapper.toModel(userDTO);

        // Hash della password
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPasswordHash(hashedPassword);

        // Puoi aggiungere qui altri campi, es. data di creazione
        user.setCreatedAt(java.time.LocalDate.now());

        // Salva nel DB
        return userRepository.save(user);
    }

    // Altri metodi come update, delete, find possono seguire una logica simile
}
