package com.example.rodenstock.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="glasses_id")
    private Glasses glasses;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public CartItem(){
    }

    public CartItem(Glasses glasses, User user){
        this.glasses=glasses;
        this.user=user;
    }

}
