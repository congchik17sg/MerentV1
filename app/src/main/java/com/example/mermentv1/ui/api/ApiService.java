package com.example.mermentv1.ui.api;

import com.example.mermentv1.model.ProductResponse;
import com.example.mermentv1.model.SigninRequest;
import com.example.mermentv1.model.SigninResponse;
import com.example.mermentv1.model.SignupRequest;
import com.example.mermentv1.model.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    // Login request
    @Headers("Content-Type: application/json")
    @POST("Authentication/login")
    Call<SigninResponse> login(@Body SigninRequest request);

    // Registration request
    @Headers({"accept: */*", "Content-Type: application/json"})
    @POST("api/Authentication/Register")
    Call<SignupResponse> registerUser(@Body SignupRequest registerRequest);

    // Fetch products
    @Headers("accept: */*")
    @GET("Product")
    Call<ProductResponse> getProducts();
}
