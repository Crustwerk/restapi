package com.crustwerk.restapi.assembler;

import com.crustwerk.restapi.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserAssembler {

    private final PasswordEncoder passwordEncoder;

    public UserAssembler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User prepareForCreation(User user, String rawPassword) {
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        user.setCreatedAt(LocalDate.now());
        return user;
    }

    public User prepareForUpdate(User user, String rawPassword) {
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        return user;
    }

}
