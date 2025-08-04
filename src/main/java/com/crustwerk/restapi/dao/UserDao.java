package com.crustwerk.restapi.dao;

import com.crustwerk.restapi.model.User;

import java.util.List;

public interface UserDao {
    boolean existsByEmail(String email);

    User getUserById(Long id);

    long createUser(User user);

    List<User> getAllUsers();

    void updateUser(User user);

    void deleteUser(Long id);
}
