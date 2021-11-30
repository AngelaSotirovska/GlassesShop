package com.example.rodenstock.repository;

import com.example.rodenstock.model.CartItem;
import com.example.rodenstock.model.Glasses;
import com.example.rodenstock.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByUser(User user);
    CartItem findByGlassesAndUser(Glasses glasses, User user);
}
