package com.example.mermentv1.model;

public class UserResponse {

    private boolean success;
    private String message;
    private UserData data;

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public UserData getData() {
        return data;
    }

    public static class UserData {
        private int id;
        private String name;
        private String email;
        private String password; // Consider removing this for security
        private String phoneNumber;
        private boolean isConfirmed;

        // Getters and setters
        public String getName() {
            return name;
        }
        // Add other getters as needed
    }

}
