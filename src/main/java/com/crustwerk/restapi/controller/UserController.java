package com.crustwerk.restapi.controller;

import com.crustwerk.restapi.Utils;
import com.crustwerk.restapi.dto.user.request.CreateUserRequest;
import com.crustwerk.restapi.dto.user.request.DeleteUserRequest;
import com.crustwerk.restapi.dto.user.request.UpdateUserRequest;
import com.crustwerk.restapi.dto.user.response.CreateUserResponse;
import com.crustwerk.restapi.dto.user.response.GetUserResponse;
import com.crustwerk.restapi.mapper.UserMapper;
import com.crustwerk.restapi.model.User;
import com.crustwerk.restapi.service.UserService;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *
 * @@Valid Fa parte di Jakarta Bean Validation.
 * Si usa per validare oggetti (bean) e i campi al loro interno, come nel caso di un parametro annotato con <b>@RequestBody</b>.<br>
 * Attiva la validazione su tutti i campi dell'oggetto.<br>
 * @@Validated Fa parte di Spring.
 * Supporta gruppi di validazione, che permettono di eseguire validazioni diverse in contesti differenti (ad esempio, creazione vs aggiornamento).<br>
 * Pu&ograve; essere usato anche su parametri primitivi come <b>@PathVariable</b> o <b>@RequestParam</b>.<br></p>
 * @Controller Espone gli endpoint REST relativi all'entità (User).
 * Si occupa esclusivamente di ricevere richieste dal client (DTO in ingresso),
 * orchestrare le chiamate a Mapper, Assembler e Service,
 * e restituire le risposte (DTO in uscita).
 * Non contiene logica di dominio n&eacute; costruzione diretta di entità.
 */

@RestController
@RequestMapping("/api/users")
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
    public ResponseEntity<CreateUserResponse> createUser(@Validated @RequestBody CreateUserRequest req) {
        LocalDate dateOfBirth = LocalDate.parse(req.dateOfBirth(), Utils.DATE_TIME_FORMATTER);
        User user = userMapper.toModel(req, dateOfBirth);
        User saved = userService.createUser(user, req.password());
        CreateUserResponse response = userMapper.toCreateUserResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) return ResponseEntity.noContent().build();

        List<GetUserResponse> dtos = new ArrayList<>();

        for (User user : users) {
            GetUserResponse dto = userMapper.toGetUserResponse(user);
            dtos.add(dto);
        }

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable @Min(1) Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        return userOptional
                .map(user -> ResponseEntity.ok(userMapper.toGetUserResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUserResponse> updateUser(@PathVariable Long id, @Validated @RequestBody UpdateUserRequest request) {
        LocalDate dateOfBirth = LocalDate.parse(request.dateOfBirth(), Utils.DATE_TIME_FORMATTER);
        User user = userMapper.toModel(request, dateOfBirth);
        Optional<User> updatedUserOptional = userService.updateUser(id, user);
        return updatedUserOptional
                .map(updatedUser -> ResponseEntity.ok(userMapper.toGetUserResponse(updatedUser)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @Validated @RequestBody DeleteUserRequest request) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
