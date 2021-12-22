package com.example.rodenstock.service.impl;


import com.example.rodenstock.model.CartItem;
import com.example.rodenstock.model.Glasses;
import com.example.rodenstock.model.Order;
import com.example.rodenstock.model.User;
import com.example.rodenstock.model.exception.GlassesNotFoundException;
import com.example.rodenstock.repository.CartItemRepository;
import com.example.rodenstock.repository.OrderRepository;
import com.example.rodenstock.service.GlassesService;
import com.example.rodenstock.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final GlassesService glassesService;

    public OrderServiceImpl(OrderRepository orderRepository, CartItemRepository cartItemRepository, GlassesService glassesService) {
        this.orderRepository = orderRepository;

        this.cartItemRepository = cartItemRepository;
        this.glassesService = glassesService;
    }
    @Override
    public List<Order> findAllByUser(User user) {
        return this.orderRepository.findAllByUser(user);
    }

    @Override
    public Optional<Order> addOrder(Long serialNumber, int quantity, User user) throws GlassesNotFoundException {
        Glasses glasses = this.glassesService.findById(serialNumber).orElseThrow(GlassesNotFoundException::new);
        Order order=null;
        if(this.findAllByUser(user).stream().anyMatch(o -> o.getSerialNumber().equals(serialNumber))){
            order = this.findAllByUser(user).stream().filter(o -> o.getSerialNumber().equals(serialNumber)).findFirst().get();
            order.setQuantity(order.getQuantity()+quantity);
        }
        else {
            order = new Order(serialNumber, quantity, glasses.getImgUrl(), glasses.getPrice(), user, glasses.getBrand());
        }
        int glassesQuantity = glasses.getQuantity();
        int left=glassesQuantity-quantity;
        if(left==0){
            glassesService.deleteById(serialNumber);
        }
        else{
            glassesService.editQuantity(serialNumber, left);
        }
        return Optional.of(this.orderRepository.save(order));
    }
}
