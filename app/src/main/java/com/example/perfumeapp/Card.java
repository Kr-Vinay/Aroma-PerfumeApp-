package com.example.perfumeapp;

public class Card {
    String imageUrl;
    String name;
    String price;
    String detail;

    public Card() {
    }

    public Card(String imageUrl, String name, String price,String detail) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.detail=detail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
