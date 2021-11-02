package com.example.rodenstock.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Glasses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;

    private Integer price;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    private Brand brand;

    public Glasses() {
    }

    public Glasses(String imgUrl, Integer price, Integer quantity, Category category, Brand brand) {
        this.imgUrl = imgUrl;
        this.price=price;
        this.quantity=quantity;
        this.category = category;
        this.brand=brand;
    }

}
