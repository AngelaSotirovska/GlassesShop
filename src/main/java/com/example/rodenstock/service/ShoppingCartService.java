package com.example.rodenstock.service;

import com.example.rodenstock.model.Glasses;
import com.example.rodenstock.model.ShoppingCart;
import com.example.rodenstock.model.exception.GlassesNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {
    List<Glasses> listAllGlasses(Long id);
    ShoppingCart addGlassesToCart(String username, Long glassesId) throws GlassesNotFoundException;
    ShoppingCart getShoppingCart(String username);
    void removeGlasses(String username, Long id) throws GlassesNotFoundException;
}
