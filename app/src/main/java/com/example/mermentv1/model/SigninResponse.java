package com.example.mermentv1.model;

public class SigninResponse {
    private boolean success;
    private String message;
    private String token;

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    // Optionally, if you need the name, you can add a field and getter here
    public String getName() {
        // Implement logic to extract name if needed
        return ""; // Replace with actual logic if necessary
    }
}
