package com.example.mermentv1.ui.card;

public class CardModel {
    private String name;
    private String price;
    private int imageResId;

    public CardModel(String name, String price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}

