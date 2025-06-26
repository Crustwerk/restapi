package com.crustwerk.restapi.controller;

import com.crustwerk.restapi.assembler.UserAssembler;
import com.crustwerk.restapi.dto.CreateUserResponse;
import com.crustwerk.restapi.dto.GetUserResponse;
import com.crustwerk.restapi.dto.CreateUserRequest;
import com.crustwerk.restapi.dto.UpdateUserRequest;
import com.crustwerk.restapi.mapper.UserMapper;
import com.crustwerk.restapi.model.User;
import com.crustwerk.restapi.service.UserService;
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

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserAssembler userAssembler;

    public UserController(UserService userService, UserMapper userMapper, UserAssembler userAssembler) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userAssembler = userAssembler;
    }

    //RequestBody fa sì che venga deserializzato il body e passato come parametro al metodo
    //In sua assenza Spring si aspetta una query string (es.?username=mario&email=...)
    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest req) {
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and Confirm Password do not match");
        }

        User base = userMapper.toModel(req);
        User ready = userAssembler.prepareForCreation(base, req.getPassword());
        User saved = userService.createUser(ready);
        CreateUserResponse response = userMapper.toCreateUserResponse(base);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<GetUserResponse> dtos = new ArrayList<>();
        for (User user : users) {
            GetUserResponse dto = userMapper.toGetUserResponse(user);
            dtos.add(dto);
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.toGetUserResponse(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and Confirm Password do not match");
        }

        User base = userMapper.toModel(request);
        User ready = userAssembler.prepareForUpdate(base, request.getPassword());
        User updatedUser = userService.updateUser(id, ready);

        return ResponseEntity.ok(userMapper.toGetUserResponse(updatedUser));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    // Puoi aggiungere altri endpoint (update, deletce...) in modo simile
}
