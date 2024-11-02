package com.example.mermentv1.ui.card;

public class CardModel {
    private String name;
    private String price;
    private String description; // Add description field
    private String imageUrl;     // Main image (URL or drawable ID)
    private String urlLeft;      // Left image (URL or drawable ID)
    private String urlRight;     // Right image (URL or drawable ID)
    private String urlSide;      // Side image (URL or drawable ID)

    // Constructor that accepts all String values (for URL usage)
    public CardModel(String name, String price, String description, String imageUrl, String urlLeft, String urlRight, String urlSide) {
        this.name = name;
        this.price = price;
        this.description = description;  // Initialize description
        this.imageUrl = imageUrl;
        this.urlLeft = urlLeft;
        this.urlRight = urlRight;
        this.urlSide = urlSide;
    }

    // Overloaded constructor to accept drawable resource IDs (int) for images
    public CardModel(String name, String price, String description, int imageUrl, int urlLeft, int urlRight, int urlSide) {
        this.name = name;
        this.price = price;
        this.description = description;  // Initialize description
        this.imageUrl = String.valueOf(imageUrl);  // Convert int to String
        this.urlLeft = String.valueOf(urlLeft);    // Convert int to String
        this.urlRight = String.valueOf(urlRight);  // Convert int to String
        this.urlSide = String.valueOf(urlSide);    // Convert int to String
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;  // Add getter for description
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrlLeft() {
        return urlLeft;
    }

    public String getUrlRight() {
        return urlRight;
    }

    public String getUrlSide() {
        return urlSide;
    }
}
