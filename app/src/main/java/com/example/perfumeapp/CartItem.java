package com.example.perfumeapp;

import java.io.Serializable;

public class CartItem implements Serializable {
    private String name;
    private String price;
    private String imageUrl;
    public int quantity;





    public CartItem(String name, String price, String imageUrl, int quantity ) {
        this.name = name;
        this.price = price.replaceAll("[^\\d.]", "");;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
