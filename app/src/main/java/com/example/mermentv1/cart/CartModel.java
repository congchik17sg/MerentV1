package com.example.mermentv1.cart;

public class CartModel {
    private String name;
    private String imageUrl;
    private int quantity;

    public CartModel(String name, String imageUrl, int quantity) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getName() {
        return name;
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
