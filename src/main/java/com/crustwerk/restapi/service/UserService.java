package com.crustwerk.restapi.service;

import com.crustwerk.restapi.exception.EmailAlreadyUsedException;
import com.crustwerk.restapi.exception.UnderageUserException;
import com.crustwerk.restapi.mapper.UserMapper;
import com.crustwerk.restapi.model.User;
import com.crustwerk.restapi.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * Contiene la logica di dominio relativa agli utenti.
 * Applica le regole del business (es. verifiche, vincoli, stati),
 * coordina le operazioni con il repository e gestisce la persistenza.
 * Riceve entità già pronte all'uso (costruite tramite Mapper e Assembler),
 * e non ha conoscenza diretta dei DTO.
 */


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new EmailAlreadyUsedException();
        }

        if (Period.between(user.getDateOfBirth(), LocalDate.now()).getYears() < 18) {
            throw new UnderageUserException();
        }


        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long id, User newUserData) {
        User existing = getUserById(id);

        existing.setUsername(newUserData.getUsername());
        existing.setEmail(newUserData.getEmail());
        existing.setDateOfBirth(newUserData.getDateOfBirth());

        return userRepository.save(existing);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    // Altri metodi come update, delete, etc
}
