package com.example.rodenstock.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Data
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long serialNumber;

    private int quantity;

    private String imgUrl;

    private Integer price;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order(){}

    public Order(Long serialNumber, int quantity, String imgUrl, Integer price, User user, Brand brand){
        this.serialNumber=serialNumber;
        this.quantity=quantity;
        this.imgUrl=imgUrl;
        this.price=price;
        this.user=user;
    }
}
