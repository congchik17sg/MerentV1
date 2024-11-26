package com.example.mermentv1.model;

import java.util.List;

public class TransactionResponse {

    private List<Transaction> data; // List of transactions from the response
    private int totalElements;      // Total number of transactions
    private int totalPages;         // Total pages available
    private int page;               // Current page number

    // Constructor
    public TransactionResponse(List<Transaction> data, int totalElements, int totalPages, int page) {
        this.data = data;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.page = page;
    }

    // Getters
    public List<Transaction> getData() {
        return data;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPage() {
        return page;
    }

}
