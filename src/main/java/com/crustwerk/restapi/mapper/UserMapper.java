package com.crustwerk.restapi.mapper;

import com.crustwerk.restapi.dto.UserDTO;
import com.crustwerk.restapi.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class UserMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    // Da DTO a Model
    public User toModel(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(dto.getPassword());
        user.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth(), formatter));
        return user;
    }

    // Da Model a DTO
    public UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        // Non si inviano password nel DTO di risposta generalmente
        dto.setPassword(null);
        dto.setConfirmPassword(null);
        dto.setDateOfBirth(user.getDateOfBirth().format(formatter));
        return dto;
    }
}
