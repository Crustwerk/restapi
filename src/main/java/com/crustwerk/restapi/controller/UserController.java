package com.crustwerk.restapi.controller;

import com.crustwerk.restapi.dto.UserDTO;
import com.crustwerk.restapi.mapper.UserMapper;
import com.crustwerk.restapi.model.User;
import com.crustwerk.restapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.createUser(userDTO);
        UserDTO responseDTO = userMapper.toDTO(user);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // GET per ottenere tutti gli utenti
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();  // devi implementare questo metodo nel service
        List<UserDTO> dtos = users.stream()
                .map(userMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    // Puoi aggiungere altri endpoint (update, delete...) in modo simile
}
