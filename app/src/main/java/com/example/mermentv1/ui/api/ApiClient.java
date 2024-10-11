package com.example.mermentv1.ui.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import okhttp3.OkHttpClient;
import okhttp3.Interceptor;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            // Set up interceptor if you need to add any common headers, for example:
            httpClient.addInterceptor(chain -> {
                // You can add additional headers here if necessary
                return chain.proceed(chain.request());
            });

            retrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .baseUrl("https://merent.uydev.id.vn/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
