package com.example.rodenstock.web;


import com.example.rodenstock.model.ShoppingCart;
import com.example.rodenstock.model.User;
import com.example.rodenstock.model.exception.GlassesNotFoundException;
import com.example.rodenstock.service.ShoppingCartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error,
                                      HttpServletRequest req,
                                      Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = req.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getShoppingCart(username);
        model.addAttribute("glasses", this.shoppingCartService.listAllGlasses(shoppingCart.getId()));
        model.addAttribute("bodyContent", "shoppingcart-page");
        return "main_template";
    }

    @PostMapping("/addglasses-shoppingcart/{id}")
    public String addProductToShoppingCart(@PathVariable Long id, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            this.shoppingCartService.addGlassesToCart(user.getUsername(), id);
            return "redirect:/shopping-cart";
        } catch (RuntimeException | GlassesNotFoundException exception) {
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }

    @RequestMapping("/remove/{id}")
    public String removeGlassesFromCart(@PathVariable(name = "id") Long id, Authentication authentication) throws GlassesNotFoundException {
        User user=(User) authentication.getPrincipal();
        this.shoppingCartService.removeGlasses(user.getUsername(), id);
        return "redirect:/shopping-cart";
    }


}
