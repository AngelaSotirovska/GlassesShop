package com.example.rodenstock.service;

import com.example.rodenstock.model.CartItem;
import com.example.rodenstock.model.Glasses;
import com.example.rodenstock.model.User;
import com.example.rodenstock.model.exception.GlassesNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CartService {
    List<CartItem> findAllByUser(User user);
    Optional<CartItem> addItemToCart(Long glassesId, User user) throws GlassesNotFoundException;
    void removeItem(Long id);
}
