package com.example.rodenstock.service.impl;

import com.example.rodenstock.model.User;
import com.example.rodenstock.model.exception.InvalidArgumentsException;
import com.example.rodenstock.model.exception.InvalidUserCredentialsException;
import com.example.rodenstock.repository.UserRepository;
import com.example.rodenstock.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if(username==null || username.isEmpty() || password==null || password.isEmpty()){
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username, password).orElseThrow(InvalidUserCredentialsException::new);
    }
}
