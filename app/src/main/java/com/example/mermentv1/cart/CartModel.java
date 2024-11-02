package com.example.mermentv1.cart;

public class CartModel {
    private String name;
    private String imageUrl;
    private int quantity;
    private String price;


    public CartModel(String name, String imageUrl, int quantity, String price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
