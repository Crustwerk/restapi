package com.crustwerk.restapi.assembler;

import com.crustwerk.restapi.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Completa e arricchisce l'entità User con informazioni derivate o tecniche
 * come l'hashing della password, i timestamp o altri campi calcolati.
 * Lavora su un'entità parzialmente costruita (di solito ottenuta da un Mapper),
 * e la rende pronta per essere utilizzata nel servizio o persistita.
 * Viene invocato solo quando serve trasformare un'entità grezza in un oggetto applicabile.
 */

@Component
public class UserAssembler {

    private final PasswordEncoder passwordEncoder;

    public UserAssembler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User prepareForCreation(User user, String rawPassword) {
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        LocalDate now = LocalDate.now();
        user.setCreatedAt(now);
        user.setLastUpdateAt(now);
        return user;
    }

    public User prepareForUpdate(User user, String rawPassword) {
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        user.setLastUpdateAt(LocalDate.now());
        return user;
    }

}
