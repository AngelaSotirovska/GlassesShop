package com.example.rodenstock.service;

import com.example.rodenstock.model.Role;
import com.example.rodenstock.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> register(String username, String password, String repeatPassword, String name, String surname, Role role);
}
