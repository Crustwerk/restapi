package com.crustwerk.restapi.controller;

import com.crustwerk.restapi.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Ciao Omar (o meglio... Crustwerk 😄), benvenuto nella tua prima API Spring Boot!";
    }

    @GetMapping("/sample")
    public User getSampleUser() {
        return new User("Omar", 30, 133.8);
    }

    @PostMapping
    public String createUser(@RequestBody User user) {
        return "Utente ricevuto: " + user.getName() + ", età: " + user.getAge() + ", peso: " + user.getWeight();
    }
}
