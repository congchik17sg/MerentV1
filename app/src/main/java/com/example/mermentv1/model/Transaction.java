package com.example.mermentv1.model;

public class Transaction {
    private String id;
    private String description;
    private double amount;
    private String status;

    public Transaction(String id, String description, double amount, String status) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }



}
