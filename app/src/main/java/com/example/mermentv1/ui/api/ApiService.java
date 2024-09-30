package com.example.mermentv1.ui.api;

import com.example.mermentv1.model.SigninRequest;
import com.example.mermentv1.model.SigninResponse;
import com.example.mermentv1.model.SignupRequest;
import com.example.mermentv1.model.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("api/Authentication/Login")
    Call<SigninResponse> login(@Body SigninRequest signinRequest);

    @Headers({"accept: */*", "Content-Type: application/json"})
    @POST("api/Authentication/Register")  // Ensure correct path
    Call<SignupResponse> registerUser(@Body SignupRequest registerRequest);

}
