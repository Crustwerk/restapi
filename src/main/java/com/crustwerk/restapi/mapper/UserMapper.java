package com.crustwerk.restapi.mapper;

import com.crustwerk.restapi.dto.CreateUserResponse;
import com.crustwerk.restapi.dto.GetUserResponse;
import com.crustwerk.restapi.dto.CreateUserRequest;
import com.crustwerk.restapi.dto.UpdateUserRequest;
import com.crustwerk.restapi.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class UserMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public User toModel(CreateUserRequest createUserRequest) {
        if (createUserRequest == null) return null;

        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setEmail(createUserRequest.getEmail());
        user.setDateOfBirth(LocalDate.parse(createUserRequest.getDateOfBirth()));
        return user;
    }

    public User toModel(UpdateUserRequest updateUserRequest) {
        if (updateUserRequest == null) return null;
        User user = new User();
        user.setUsername(updateUserRequest.getUsername());
        user.setEmail(updateUserRequest.getEmail());
        user.setDateOfBirth(LocalDate.parse(updateUserRequest.getDateOfBirth()));
        return user;
    }

    public CreateUserResponse toCreateUserResponse(User user) {
        if (user == null) return null;

        CreateUserResponse response = new CreateUserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setDateOfBirth(user.getDateOfBirth().toString());
        return response;
    }

    public GetUserResponse toGetUserResponse(User user) {
        if (user == null) return null;

        GetUserResponse response = new GetUserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setDateOfBirth(user.getDateOfBirth().toString());
        return response;
    }
}
