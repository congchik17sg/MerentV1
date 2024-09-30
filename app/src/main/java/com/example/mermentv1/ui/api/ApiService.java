package com.example.apitest.api;

import com.example.apitest.LoginRequest;
import com.example.apitest.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("api/Authentication/Login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
