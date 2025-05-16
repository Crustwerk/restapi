package com.crustwerk.restapi.repository;

import com.crustwerk.restapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // puoi aggiungere metodi custom qui se serve, es:
    // Optional<User> findByUsername(String username);
}
