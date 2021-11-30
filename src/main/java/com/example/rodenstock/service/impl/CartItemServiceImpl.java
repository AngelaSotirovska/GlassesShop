package com.example.rodenstock.service.impl;


import com.example.rodenstock.model.CartItem;
import com.example.rodenstock.model.Glasses;
import com.example.rodenstock.model.User;
import com.example.rodenstock.model.exception.GlassesAlreadyInCartException;
import com.example.rodenstock.model.exception.GlassesNotFoundException;
import com.example.rodenstock.repository.CartItemRepository;
import com.example.rodenstock.repository.GlassesRepository;
import com.example.rodenstock.service.CartService;
import com.example.rodenstock.service.GlassesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final GlassesService glassesService;


    public CartItemServiceImpl(CartItemRepository cartItemRepository, GlassesService glassesService) {
        this.cartItemRepository = cartItemRepository;
        this.glassesService = glassesService;
    }


    @Override
    public List<CartItem> findAllByUser(User user) {
        return cartItemRepository.findAllByUser(user);
    }

    @Override
    public Optional<CartItem> addItemToCart(Long Id, User user) throws GlassesNotFoundException {
        Glasses glasses=this.glassesService.findById(Id).orElseThrow(GlassesNotFoundException::new);
        CartItem cartItem=this.cartItemRepository.findByGlassesAndUser(glasses, user);
        if(cartItem!=null){
            throw new GlassesAlreadyInCartException();
        }
        cartItem=new CartItem(glasses, user);
        return Optional.of(cartItemRepository.save(cartItem));
    }

    @Override
    public void removeItem(Long id){
        this.cartItemRepository.deleteById(id);
    }
}
