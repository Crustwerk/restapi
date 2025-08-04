package com.crustwerk.restapi.service;

import com.crustwerk.restapi.dao.UserDaoImpl;
import com.crustwerk.restapi.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserDaoImpl userDaoImpl;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Test
    void createUser_WithValidUser_Successful() {
        //Given
        User user = new User();
        String email = "pippo@pluto.it";
        user.setEmail(email);

        Long userId = 1L;
        when(userDaoImpl.existsByEmail(email)).thenReturn(false);
        when(userDaoImpl.createUser(user)).thenReturn(userId);

        User savedUser = new User();
        savedUser.setId(userId);

        when(userDaoImpl.getUserById(userId)).thenReturn(savedUser);
        String password = "provaprova";

        //When
        User result = userService.createUser(user, password);

        //Then
        assertThat(result).isNotNull();


        assertThat(result.getId()).isNotNull();

        verify(userDaoImpl).existsByEmail(email);
        verify(userDaoImpl).createUser(user);
        verify(passwordEncoder).encode(password);

    }
}
