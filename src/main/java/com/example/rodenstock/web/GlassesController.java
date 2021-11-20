package com.example.rodenstock.web;


import com.example.rodenstock.model.*;
import com.example.rodenstock.model.exception.BrandNotFoundException;
import com.example.rodenstock.model.exception.CategoryNotFoundException;
import com.example.rodenstock.model.exception.GlassesNotFoundException;
import com.example.rodenstock.service.BrandService;
import com.example.rodenstock.service.GlassesService;
import com.example.rodenstock.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/glasses")
public class GlassesController {

    private final GlassesService glassesService;
    private final BrandService brandService;


    public GlassesController(GlassesService glassesService, BrandService brandService) {
        this.glassesService = glassesService;
        this.brandService = brandService;
    }

    @GetMapping
    public String getGlassesPage(@RequestParam(required = false) String category, @RequestParam(required = false) String error, Model model){
        List<Glasses> glasses=this.glassesService.listAll();
        if(category!=null){
            glasses=glasses.stream().filter(i->i.getCategory().getName().equals(category)).collect(Collectors.toList());
        }
        model.addAttribute("glasses", glasses);
        model.addAttribute("bodyContent", "glasses");
        return "main_template";
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id, Authentication authentication) throws GlassesNotFoundException {
        User user = (User) authentication.getPrincipal();
        glassesService.deleteFromShoppingCart(id, user);
        glassesService.deleteById(id);
        return "redirect:/glasses";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addFromGlasses(Model model){
        Glasses glasses=new Glasses();
        model.addAttribute("glasses", glasses);
        List<Brand> brands=this.brandService.listAll();
        model.addAttribute("brands", brands);
        List<Category> categories= List.of(Category.values());
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-glasses");
        return "main_template";
    }

    @GetMapping("/add-brand-form")
    public String addFormBrand(Model model){
        Brand brand=new Brand();
        model.addAttribute("brand", brand);
        model.addAttribute("bodyContent", "add-brand");
        return "main_template";
    }

    @GetMapping("/edit-form/{id}")
    public String editFormGlasses(@PathVariable Long id,  Model model){
        if(this.glassesService.findById(id).isPresent()) {
            Glasses glasses = this.glassesService.findById(id).get();
            List<Brand> brands = this.brandService.listAll();
            List<Category> categories = List.of(Category.values());
            model.addAttribute("glasses", glasses);
            model.addAttribute("brands", brands);
            model.addAttribute("categories", categories);
            model.addAttribute("bodyContent", "add-glasses");
            return "main_template";
        }
        return "redirect:/glasses?error=GlassesNotFound";
    }

    @PostMapping("/add")
    public String addGlasses(@RequestParam(required = false) Long id, @RequestParam String imgUrl, @RequestParam Integer price, @RequestParam Integer quantity, @RequestParam String category, @RequestParam Long brand) throws BrandNotFoundException, CategoryNotFoundException, GlassesNotFoundException {
        if(id != null){
            this.glassesService.edit(id, imgUrl, price, quantity, category, brand);
        }
        else {
            this.glassesService.save(imgUrl, price, quantity, category, brand);
        }
        return "redirect:/glasses";
    }

    @PostMapping("/addBrand")
    public String addBrand(@RequestParam String name, @RequestParam String country){
        this.brandService.save(name, country);
        return "redirect:/glasses";
    }

    @GetMapping("/showglasses/{id}")
    public String showOnePair(@PathVariable Long id, Model model){
        Glasses glasses=this.glassesService.findById(id).get();
        model.addAttribute("glasses", glasses);
        model.addAttribute("bodyContent", "clicked-pair");
        return "main_template";
    }

    @GetMapping("/access_denied")
    public String getAccessDeniedPage(Model model) {
        model.addAttribute("bodyContent", "access_denied");
        return "main_template";
    }

    @GetMapping("/contact")
    public String getContactPage(Model model){
        model.addAttribute("bodyContent", "contact");
        return "main_template";
    }

}
