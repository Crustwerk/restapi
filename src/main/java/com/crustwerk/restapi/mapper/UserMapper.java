package com.crustwerk.restapi.mapper;

import com.crustwerk.restapi.dto.user.request.CreateUserRequest;
import com.crustwerk.restapi.dto.user.request.UpdateUserRequest;
import com.crustwerk.restapi.dto.user.response.CreateUserResponse;
import com.crustwerk.restapi.dto.user.response.GetUserResponse;
import com.crustwerk.restapi.model.User;
import org.springframework.stereotype.Component;

import static com.crustwerk.restapi.dto.user.response.CreateUserResponse.CreateUserResponseBuilder;
import static com.crustwerk.restapi.dto.user.response.GetUserResponse.GetUserResponseBuilder;

/**
 * Responsabile della conversione semplice e diretta tra DTO e entità User (e viceversa).
 * Esegue il mapping campo per campo senza applicare logica di dominio né arricchimenti.
 * Viene usato esclusivamente nei controller per trasformare dati in ingresso e in uscita.
 */

@Component
public class UserMapper {

    public User toModel(CreateUserRequest createUserRequest) {
        if (createUserRequest == null) return null;

        User user = new User();
        user.setUsername(createUserRequest.username());
        user.setEmail(createUserRequest.email());
        user.setDateOfBirth(createUserRequest.dateOfBirth());
        return user;
    }

    public User toModel(UpdateUserRequest updateUserRequest) {
        if (updateUserRequest == null) return null;
        User user = new User();
        user.setUsername(updateUserRequest.username());
        user.setEmail(updateUserRequest.email());
        user.setDateOfBirth(updateUserRequest.dateOfBirth());
        return user;
    }

    public CreateUserResponse toCreateUserResponse(User user) {
        if (user == null) return null;

        return CreateUserResponseBuilder
                .aCreateUserResponse()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withEmail(user.getEmail())
                .withDateOfBirth(user.getDateOfBirth())
                .build();
    }

    public GetUserResponse toGetUserResponse(User user) {
        if (user == null) return null;

        return GetUserResponseBuilder
                .aGetUserResponse()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withEmail(user.getEmail())
                .withDateOfBirth(user.getDateOfBirth())
                .build();
    }
}
