package com.example.rodenstock.service;

import com.example.rodenstock.model.Brand;
import com.example.rodenstock.model.Order;
import com.example.rodenstock.model.User;
import com.example.rodenstock.model.exception.GlassesNotFoundException;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAllByUser(User user);
    Optional<Order> addOrder(Long serialNumber, int quantity, User user) throws GlassesNotFoundException;
}
