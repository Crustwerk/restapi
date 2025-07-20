package com.crustwerk.restapi.service;

import com.crustwerk.restapi.dao.UserDao;
import com.crustwerk.restapi.exception.EmailAlreadyUsedException;
import com.crustwerk.restapi.model.User;
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

    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    // Creazione di un nuovo User
    public User createUser(User user, String rawPassword) {
        // Verifica se l'email esiste già
        if (userDao.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        // Cripta la password
        user.setPasswordHash(passwordEncoder.encode(rawPassword));

        // Imposta le date
        LocalDate now = LocalDate.now();
        user.setCreatedAt(now);
        user.setLastUpdateAt(now);

        // Salviamo l'utente nel database
        userDao.addUser(user);
        return user;
    }

    // Recupera tutti gli utenti
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    // Recupera un User per ID
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    // Aggiorna un User
    public User updateUser(Long id, User newUserData, String rawPassword) {
        User existing = getUserById(id);

        existing.setUsername(newUserData.getUsername());
        existing.setEmail(newUserData.getEmail());
        existing.setDateOfBirth(newUserData.getDateOfBirth());
        existing.setLastUpdateAt(LocalDate.now());
        existing.setPasswordHash(passwordEncoder.encode(rawPassword));

        userDao.updateUser(existing);
        return existing;
    }

    // Elimina un User
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }
}