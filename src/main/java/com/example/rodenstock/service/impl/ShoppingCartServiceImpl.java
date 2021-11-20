package com.example.rodenstock.service.impl;


import com.example.rodenstock.model.Glasses;
import com.example.rodenstock.model.ShoppingCart;
import com.example.rodenstock.model.User;
import com.example.rodenstock.model.exception.GlassesAlreadyInCartException;
import com.example.rodenstock.model.exception.GlassesNotFoundException;
import com.example.rodenstock.model.exception.ShoppingCartNotFoundException;
import com.example.rodenstock.repository.ShoppingCartRepository;
import com.example.rodenstock.repository.UserRepository;
import com.example.rodenstock.service.GlassesService;
import com.example.rodenstock.service.ShoppingCartService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final GlassesService glassesService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, GlassesService glassesService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.glassesService = glassesService;
    }

    @Override
    public List<ShoppingCart> listShoppingCarts() {
        return this.shoppingCartRepository.findAll();
    }

    @Override
    public List<Glasses> listAllGlasses(Long id) {
        if(!this.shoppingCartRepository.findById(id).isPresent()) {
            throw new ShoppingCartNotFoundException();
        }
        return this.shoppingCartRepository.findById(id).get().getGlassesList();
    }

    @Override
    public ShoppingCart addGlassesToCart(String username, Long glassesId) throws GlassesNotFoundException {
        ShoppingCart shoppingCart=this.getShoppingCart(username);
        Glasses glasses=this.glassesService.findById(glassesId).orElseThrow(GlassesNotFoundException::new);
        if(shoppingCart.getGlassesList()
                .stream().filter(i -> i.getId().equals(glassesId))
                .collect(Collectors.toList()).size() > 0)
            throw new GlassesAlreadyInCartException();

        shoppingCart.getGlassesList().add(glasses);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getShoppingCart(String username) {
        User user=this.userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));

        return this.shoppingCartRepository.findByUser(user).orElseGet(()->{
            ShoppingCart cart=new ShoppingCart(user);
            return this.shoppingCartRepository.save(cart);
        });
    }

    @Override
    public void removeGlasses(String username, Long id) throws GlassesNotFoundException {
        ShoppingCart shoppingCart=this.getShoppingCart(username);
        Glasses glasses=this.glassesService.findById(id).orElseThrow(GlassesNotFoundException::new);
        shoppingCart.getGlassesList().remove(glasses);
        this.shoppingCartRepository.save(shoppingCart);
    }
}
