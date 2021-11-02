package com.example.rodenstock.model.exception;

public class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException() {
        super("Category not found!");
    }
}
