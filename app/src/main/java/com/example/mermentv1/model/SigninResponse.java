package com.example.mermentv1.model;

public class LoginResponse {
    private String token;  // Adjust according to your API response
    private String message;  // Example

    // Getters and setters (optional, if needed)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
