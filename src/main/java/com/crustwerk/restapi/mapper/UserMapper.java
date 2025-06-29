package com.crustwerk.restapi.mapper;

import com.crustwerk.restapi.dto.user.request.CreateUserRequest;
import com.crustwerk.restapi.dto.user.request.UpdateUserRequest;
import com.crustwerk.restapi.dto.user.response.CreateUserResponse;
import com.crustwerk.restapi.dto.user.response.GetUserResponse;
import com.crustwerk.restapi.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        user.setUsername(createUserRequest.getUsername());
        user.setEmail(createUserRequest.getEmail());
        user.setDateOfBirth(createUserRequest.getDateOfBirth());
        return user;
    }

    public User toModel(UpdateUserRequest updateUserRequest) {
        if (updateUserRequest == null) return null;
        User user = new User();
        user.setUsername(updateUserRequest.getUsername());
        user.setEmail(updateUserRequest.getEmail());
        user.setDateOfBirth(updateUserRequest.getDateOfBirth());
        return user;
    }

    public CreateUserResponse toCreateUserResponse(User user) {
        if (user == null) return null;

        CreateUserResponse response = new CreateUserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setDateOfBirth(user.getDateOfBirth());
        return response;
    }

    public GetUserResponse toGetUserResponse(User user) {
        if (user == null) return null;

        GetUserResponse response = new GetUserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setDateOfBirth(user.getDateOfBirth());
        return response;
    }
}
