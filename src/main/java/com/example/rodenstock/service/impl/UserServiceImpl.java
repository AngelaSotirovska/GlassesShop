package com.example.rodenstock.service.impl;

import com.example.rodenstock.model.Role;
import com.example.rodenstock.model.User;
import com.example.rodenstock.model.exception.InvalidArgumentsException;
import com.example.rodenstock.model.exception.PasswordsDoNotMatchException;
import com.example.rodenstock.model.exception.UsernameAlreadyExistsException;
import com.example.rodenstock.repository.UserRepository;
import com.example.rodenstock.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> register(String username, String password, String repeatPassword, String name, String surname, Role role) {
        if(username==null || username.isEmpty() || password==null || password.isEmpty()){
            throw new InvalidArgumentsException();
        }
        if(!password.equals(repeatPassword)){
            throw new PasswordsDoNotMatchException();
        }
        if(this.userRepository.findByUsername(username).isPresent()){
            throw new UsernameAlreadyExistsException();
        }
        User user=new User(username, passwordEncoder.encode(password), name, surname, role);
        return Optional.of(this.userRepository.save(user));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(()->new UsernameNotFoundException(s));
    }
}
