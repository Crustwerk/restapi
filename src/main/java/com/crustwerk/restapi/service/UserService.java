package com.crustwerk.restapi.service;

import com.crustwerk.restapi.dao.UserDaoImpl;
import com.crustwerk.restapi.exception.EmailAlreadyUsedException;
import com.crustwerk.restapi.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Contiene la logica di dominio relativa agli utenti.
 * Applica le regole del business (es. verifiche, vincoli, stati),
 * coordina le operazioni con il repository e gestisce la persistenza.
 * Riceve entità già pronte all'uso (costruite tramite Mapper e Assembler),
 * e non ha conoscenza diretta dei DTO.
 */

@Service
public class UserService {

    private final UserDaoImpl userDaoImpl;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserDaoImpl userDaoImpl, BCryptPasswordEncoder passwordEncoder) {
        this.userDaoImpl = userDaoImpl;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user, String rawPassword) {
        if (userDaoImpl.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        user.setPasswordHash(passwordEncoder.encode(rawPassword));

        LocalDate now = LocalDate.now();
        user.setCreatedAt(now);
        user.setLastUpdateAt(now);

        userDaoImpl.addUser(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userDaoImpl.getAllUsers();
    }

    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userDaoImpl.getUserById(id));
    }

    public Optional<User> updateUser(Long id, User newUserData) {
        Optional<User> existingOptional = getUserById(id);

        existingOptional.ifPresent(user -> {
            user.setUsername(newUserData.getUsername());
            user.setEmail(newUserData.getEmail());
            user.setDateOfBirth(newUserData.getDateOfBirth());
            user.setLastUpdateAt(LocalDate.now());
            userDaoImpl.updateUser(user);
        });

        return existingOptional;
    }

    public void deleteUser(Long id) {
        userDaoImpl.deleteUser(id);
    }
}