package com.example.mermentv1.model;

public class SigninResponse {
    private String token;  // JWT or authentication token
    private String message;  // Example message returned from the API
    private String username; // Assuming the API response includes the username

    // Getters and setters
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

    public String getUsername() {
        return username; // Getter for the username
    }

    public void setUsername(String username) {
        this.username = username; // Setter for the username
    }
}
