package com.crustwerk.restapi.controller;

import com.crustwerk.restapi.Utils;
import com.crustwerk.restapi.dto.user.request.CreateUserRequest;
import com.crustwerk.restapi.dto.user.request.DeleteUserRequest;
import com.crustwerk.restapi.dto.user.request.UpdateUserRequest;
import com.crustwerk.restapi.dto.user.response.CreateUserResponse;
import com.crustwerk.restapi.dto.user.response.GetUserResponse;
import com.crustwerk.restapi.exception.LegalAgeUserException;
import com.crustwerk.restapi.mapper.UserMapper;
import com.crustwerk.restapi.model.User;
import com.crustwerk.restapi.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest req) throws LegalAgeUserException {
        if (!req.password().equals(req.confirmPassword())) {
            throw new IllegalArgumentException("Password and Confirm Password do not match");
        }

        LocalDate dateOfBirth = LocalDate.parse(req.dateOfBirth(), Utils.DATE_TIME_FORMATTER);
        if (dateOfBirth.isBefore(LocalDate.now().minusYears(18))) {
            throw new LegalAgeUserException();
        }

        User user = userMapper.toModel(req, dateOfBirth);
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
