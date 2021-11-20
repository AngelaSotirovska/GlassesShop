package com.example.rodenstock.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createDate;

    @ManyToOne
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "shopping_cart_glasses_list",
            joinColumns = @JoinColumn(name = "shopping_cart_id"),
            inverseJoinColumns = @JoinColumn(name = "glasses_id")
    )
    private List<Glasses> glassesList;

    public ShoppingCart() {
    }

    public ShoppingCart(User user) {
        this.createDate = LocalDateTime.now();
        this.user = user;
        this.glassesList = new ArrayList<>();
    }

}
