package com.example.rodenstock.service;

import com.example.rodenstock.model.User;

public interface AuthService {
    User login(String username, String password);
}
