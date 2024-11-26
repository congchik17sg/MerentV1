package com.example.mermentv1.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ComboItem {

    private int comboID;
    private String comboName;
    private String description;
    private String urlImg;
    private List<ProductItem> products; // List of products in this combo
    private double totalPrice;

    // Constructor
    public ComboItem(int comboID, String comboName, String description, String urlImg, List<ProductItem> products, int totalPrice) {
        this.comboID = comboID;
        this.comboName = comboName;
        this.description = description;
        this.urlImg = urlImg;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getComboID() { return comboID; }
    public void setComboID(int comboID) { this.comboID = comboID; }
    public String getComboName() { return comboName; }
    public void setComboName(String comboName) { this.comboName = comboName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUrlImg() { return urlImg; }
    public void setUrlImg(String urlImg) { this.urlImg = urlImg; }
    public List<ProductItem> getProducts() { return products; }
    public void setProducts(List<ProductItem> products) { this.products = products; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }

    @Override
    public String toString() {
        return "ComboItem{" +
                "comboName='" + comboName + '\'' +
                ", description='" + description + '\'' +
                ", urlImg='" + urlImg + '\'' +
                '}';
    }

}
