package com.example.mermentv1.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private String productType;
    private String urlCenter;
    private String urlLeft;
    private String urlRight;
    private String urlSide;
    private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlCenter() {
        return urlCenter;
    }

    public void setUrlCenter(String urlCenter) {
        this.urlCenter = urlCenter;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getUrlLeft() {
        return urlLeft;
    }

    public void setUrlLeft(String urlLeft) {
        this.urlLeft = urlLeft;
    }

    public String getUrlRight() {
        return urlRight;
    }

    public void setUrlRight(String urlRight) {
        this.urlRight = urlRight;
    }

    public String getUrlSide() {
        return urlSide;
    }

    public void setUrlSide(String urlSide) {
        this.urlSide = urlSide;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
