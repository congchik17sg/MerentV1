package com.example.mermentv1.model;

public class SignupRequest {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String gender;


    public SignupRequest(String name, String email, String password, String phoneNumber, String gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }
}
