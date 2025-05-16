package com.crustwerk.restapi.controller;

import com.crustwerk.restapi.dto.UserDTO;
import com.crustwerk.restapi.mapper.UserMapper;
import com.crustwerk.restapi.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserMapper userMapper;

    // Qui potresti avere un service reale, ma per ora useremo una lista finta
    private final List<User> users = new ArrayList<>();

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        // Converto DTO in model
        User user = userMapper.toModel(userDTO);
        users.add(user);

        // Converto model in DTO per risposta
        UserDTO responseDTO = userMapper.toDTO(user);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // GET per ottenere tutti gli utenti
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(userMapper.toDTO(user));
        }
        return ResponseEntity.ok(dtos);
    }

    // Puoi aggiungere altri endpoint (update, delete...) in modo simile
}
