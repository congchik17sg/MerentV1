package com.example.mermentv1.model;

public class SignupResponse {

    private Object data;
    private boolean success;
    private String message;
    private Object error;
    private Object errorMessages;

    // Getters
    public Object getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getError() {
        return error;
    }

    public Object getErrorMessages() {
        return errorMessages;
    }
}
