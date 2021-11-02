package com.example.rodenstock.model.exception;

public class BrandNotFoundException extends Exception {
    public BrandNotFoundException() {
        super("Brand not found!");
    }
}
