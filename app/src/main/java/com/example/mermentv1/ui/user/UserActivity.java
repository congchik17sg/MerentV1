package com.example.mermentv1.ui.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mermentv1.R;
import com.example.mermentv1.ui.api.ApiService;
import com.example.mermentv1.model.UserResponse;
import com.example.mermentv1.ui.api.ApiClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);

        textView = findViewById(R.id.name);
        setupWindowInsets();
        setupNavigation();

        // Fetch user data
        fetchCurrentUser();
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void fetchCurrentUser() {
        // Retrieve the token from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        if (token == null) {
            textView.setText("No token found. Please log in again.");
            return; // Exit the method if no token is found
        }

        // Pass the context to getClient()
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Call<UserResponse> call = apiService.getUserDetails("Bearer " + token);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();
                    if (userResponse.isSuccess()) {
                        String name = userResponse.getData().getName();
                        textView.setText(name); // Display user's name
                    } else {
                        textView.setText("Error: " + userResponse.getMessage());
                    }
                } else {
                    textView.setText("Failed to fetch user data.");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                textView.setText("Error: " + t.getMessage());
            }
        });
    }

    private void setupNavigation() {
        LinearLayout thanhToanLayout = findViewById(R.id.thanh_toan_layout);
        thanhToanLayout.setOnClickListener(v -> navigateToActivity(ContactActivity.class));

        LinearLayout diaChiLayout = findViewById(R.id.dia_chi_layout);
        diaChiLayout.setOnClickListener(v -> navigateToActivity(AddressActivity.class));

        LinearLayout caiDatLayout = findViewById(R.id.cai_dat_layout);
        caiDatLayout.setOnClickListener(v -> navigateToActivity(SettingActivity.class));
    }

    private void navigateToActivity(Class<?> targetActivity) {
        Intent intent = new Intent(UserActivity.this, targetActivity);
        startActivity(intent);
    }
}
