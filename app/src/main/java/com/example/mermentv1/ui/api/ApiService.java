package com.example.mermentv1.ui.api;

import com.example.mermentv1.model.PaymentRequest;
import com.example.mermentv1.model.PaymentResponse;
import com.example.mermentv1.model.ProductResponse;
import com.example.mermentv1.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // Login request
    @Headers("Content-Type: application/json")
    @POST("Authentication/login")
    Call<SigninResponse> login(@Body SigninRequest request);

    // Registration request
    @Headers({"accept: */*", "Content-Type: application/json"})
    @POST("Authentication/Register")
    Call<SignupResponse> registerUser(@Body SignupRequest registerRequest);

//    // Fetch all products
//    @Headers("accept: */*")
//    @GET("Product")
//    Call<ProductResponse> getProducts();

    // Fetch products by category
    @GET("Product")
    Call<ProductResponse> getProductsByCategory(@Query("category") String category);

    @GET("Product")
    Call<ProductResponse> getAllProducts();


    @GET("User/me")
    Call<UserResponse> getUserDetails(@Header("Authorization") String token);

    @POST("Wallet/create-payment-link-payos")
    Call<PaymentResponse> createPaymentLink(
            @Header("Authorization") String authToken,
            @Body PaymentRequest paymentRequest
    );
}
