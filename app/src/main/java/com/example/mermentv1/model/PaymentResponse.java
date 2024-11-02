package com.example.mermentv1.model;

public class PaymentResponse {

    private String data;
    private boolean success;
    private String message;
    private String error;
    private String errorMessages;

    // Getters
    public String getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
