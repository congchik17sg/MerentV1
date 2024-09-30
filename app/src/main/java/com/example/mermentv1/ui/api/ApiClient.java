package com.example.mermentv1.ui.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://10.0.2.2:7253/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            // Use the unsafe HTTP client for SSL bypass
            OkHttpClient unsafeHttpClient = UnsafeHttpClient.getUnsafeOkHttpClient();

            retrofit = new Retrofit.Builder()
                    .client(unsafeHttpClient)  // Add the unsafe HTTP client here
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
