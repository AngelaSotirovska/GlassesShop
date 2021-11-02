package com.example.rodenstock.repository;

import com.example.rodenstock.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
   Optional<User> findByUsernameAndPassword(String username, String password);
   Optional<User> findByUsername(String username);
}
