package com.example.rodenstock.model;

public enum Category {
    MALE ("Male"),
    FEMALE ("Female"),
    KIDS ("Kids");

    private final String name;

    Category(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public static Category findByText(String text) {
        for (Category b : Category.values()) {
            if (b.name.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
