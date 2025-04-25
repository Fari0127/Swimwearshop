package com.example.swimwearshop;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private double price;
    private int imageResId;

    public Product(String name, double price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}