package com.example.rodenstock.web;


import com.example.rodenstock.model.CartItem;
import com.example.rodenstock.model.User;
import com.example.rodenstock.model.exception.GlassesNotFoundException;
import com.example.rodenstock.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final CartService cartService;

    public ShoppingCartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error,
                                      Authentication authentication,
                                      Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        User user = (User) authentication.getPrincipal();
        List<CartItem> cartItems = cartService.findAllByUser(user);
        model.addAttribute("glasses", cartItems);
        model.addAttribute("bodyContent", "shoppingcart-page");
        return "main_template";
    }

    @PostMapping("/addglasses-shoppingcart/{id}")
    public String addProductToShoppingCart(@PathVariable Long id, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            this.cartService.addItemToCart(id, user);
            return "redirect:/shopping-cart";
        } catch (RuntimeException | GlassesNotFoundException exception) {
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }

    @RequestMapping("/remove/{id}")
    public String removeGlassesFromCart(@PathVariable(name = "id") Long id, Authentication authentication) throws GlassesNotFoundException {
        User user=(User) authentication.getPrincipal();
        this.cartService.removeItem(id);
        return "redirect:/shopping-cart";
    }


}
