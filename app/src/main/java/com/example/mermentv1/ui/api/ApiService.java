package com.example.mermentv1.ui.api;

import com.example.mermentv1.model.ComboItem;
//import com.example.mermentv1.model.ComboResponse;
import com.example.mermentv1.model.ComboResponse;
import com.example.mermentv1.model.ProductItem;
import com.example.mermentv1.model.ProductOrder;
import com.example.mermentv1.model.PaymentRequest;
import com.example.mermentv1.model.PaymentResponse;
import com.example.mermentv1.model.ProductResponse;
import com.example.mermentv1.model.SigninRequest;
import com.example.mermentv1.model.SigninResponse;
import com.example.mermentv1.model.SignupRequest;
import com.example.mermentv1.model.SignupResponse;
import com.example.mermentv1.model.TransactionResponse;
import com.example.mermentv1.model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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

    @PUT("User/{id}")
    Call<Void> updateUserInfo(
            @Header("Authorization") String token,
            @Path("id") int userId,
            @Body UserResponse.UserData updatedData
    );


    @GET("Transaction/transactions-user")
    Call<TransactionResponse> getUserTransactions(
            @Header("Authorization") String token,
            @Query("walletTypeEnums") String walletType,
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("ProductOrder/user/{userId}")
    Call<List<ProductOrder>> getUserOrders(
            @Header("Authorization") String token,
            @Query("userID") int userID);

    @GET("ProductOrder/user/{userId}")
    Call<List<ProductOrder>> getProductOrdersByUserId(@Path("userId") int userId);

    @GET("ProductOrder/user/{user_id}")
    Call<List<ProductOrder>> getProductOrdersByUser(@Path("user_id") int userId);

//    @GET("ComboOfProduct/All-Product-ByCombo")
//    Call<List<ComboItem>> getComboProducts(); // Endpoint to fetch all combos

    @GET("ComboOfProduct/All-Product-ByCombo")
    Call<ComboResponse> getComboProducts();


    @GET("combos/{comboID}/products")
    Call<List<ProductItem>> getProductsByCombo(@Path("comboID") int comboID);

//    @GET("ComboOfProduct/All-Product-ByCombo")
//    Call<ComboResponse> getAllComboProducts();


}
