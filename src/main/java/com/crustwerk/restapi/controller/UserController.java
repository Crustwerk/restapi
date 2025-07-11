package com.crustwerk.restapi.controller;

import com.crustwerk.restapi.dto.user.request.CreateUserRequest;
import com.crustwerk.restapi.dto.user.request.DeleteUserRequest;
import com.crustwerk.restapi.dto.user.request.UpdateUserRequest;
import com.crustwerk.restapi.dto.user.response.CreateUserResponse;
import com.crustwerk.restapi.dto.user.response.GetUserResponse;
import com.crustwerk.restapi.mapper.UserMapper;
import com.crustwerk.restapi.model.User;
import com.crustwerk.restapi.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @@Validated Fa parte del framework Spring e si usa a livello di classe.
 * Serve per attivare le validazioni dei parametri primitivi (es. @PathVariable @Min(1) Long id) e la validazione a gruppi (avanzato, todo).
 * @@Valid Fa parte di jakarta (ex. javax).
 * Serve per attivare le validazioni definite nei bean, dunque si usa esclusivamente sui parametri di tipo oggetto (es. @Valid @RequestBody CreateUserRequest req)
 * @Controller Espone gli endpoint REST relativi all'entità User.
 * Si occupa esclusivamente di ricevere richieste dal client (DTO in ingresso),
 * orchestrare le chiamate a Mapper, Assembler e Service,
 * e restituire le risposte (DTO in uscita).
 * Non contiene logica di dominio né costruzione diretta di entità.
 */

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

    /**
     * @@RequestBody Fa sì che venga de-serializzato il body e passato come parametro al metodo.
     * In sua assenza Spring si aspetta una query string (es.?username=mario&email=...)
     */
    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest req) {
        if (!req.password().equals(req.confirmPassword())) {
            throw new IllegalArgumentException("Password and Confirm Password do not match");
        }

        User user = userMapper.toModel(req);
        User saved = userService.createUser(user, req.password());
        CreateUserResponse response = userMapper.toCreateUserResponse(saved);
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

    //TODO adeguare a SubscriptionController (Optional)
    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable @Min(1) Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.toGetUserResponse(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        if (!request.password().equals(request.confirmPassword())) {
            throw new IllegalArgumentException("Password and Confirm Password do not match");
        }

        User user = userMapper.toModel(request);
        User updatedUser = userService.updateUser(id, user, request.password());

        return ResponseEntity.ok(userMapper.toGetUserResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @Valid @RequestBody DeleteUserRequest request) {
        if (!request.password().equals(request.confirmPassword())) {
            throw new IllegalArgumentException("Password and Confirm Password do not match");
        }

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
