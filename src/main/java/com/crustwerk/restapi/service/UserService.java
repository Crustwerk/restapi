package com.crustwerk.restapi.service;

import com.crustwerk.restapi.exception.EmailAlreadyUsedException;
import com.crustwerk.restapi.model.User;
import com.crustwerk.restapi.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user, String rawPassword) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        LocalDate now = LocalDate.now();
        user.setCreatedAt(now);
        user.setLastUpdateAt(now);

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long id, User newUserData, String rawPassword) {
        User existing = getUserById(id);

        existing.setUsername(newUserData.getUsername());
        existing.setEmail(newUserData.getEmail());
        existing.setDateOfBirth(newUserData.getDateOfBirth());
        existing.setLastUpdateAt(LocalDate.now());
        existing.setPasswordHash(passwordEncoder.encode(rawPassword));

        return userRepository.save(existing);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
