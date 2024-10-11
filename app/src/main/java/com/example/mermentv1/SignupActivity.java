package com.example.mermentv1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mermentv1.model.SignupRequest;
import com.example.mermentv1.model.SignupResponse;
import com.example.mermentv1.ui.api.ApiClient;
import com.example.mermentv1.ui.api.ApiService;
import com.example.mermentv1.ui.api.UnsafeHttpClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Navigate to login when clicking the login button
        TextView btnSignIn = findViewById(R.id.btn_signin);
        btnSignIn.setOnClickListener(v -> {
            // Handle navigation to the login activity here
            Toast.makeText(SignupActivity.this, "Navigating to login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
            startActivity(intent);
        });


        OkHttpClient unsafeHttpClient = UnsafeHttpClient.getUnsafeOkHttpClient();

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(unsafeHttpClient)
                .baseUrl("https://10.0.2.2:7253/")  // Adjust to your actual base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Handle user registration when clicking the signup button
        Button btnSignup = findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(v -> {
            String name = ((EditText) findViewById(R.id.edt_username)).getText().toString();
            String email = ((EditText) findViewById(R.id.edt_email)).getText().toString();
            String password = ((EditText) findViewById(R.id.edt_password)).getText().toString();
            String phoneNumber = "123456789";  // Example phone number
            String gender = "male";  // Example gender

//            if (validateInputs(name, email, password)) {
//                registerUser(name, email, password, phoneNumber, gender);
//            } else {
//                Toast.makeText(SignupActivity.this, "Please check your input fields", Toast.LENGTH_SHORT).show();
//            }
        });
    }

    // Validate input fields
    private boolean validateInputs(String name, String email, String password) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (name.isEmpty()) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Register user using the API
//    private void registerUser(String name, String email, String password, String phoneNumber, String gender) {
//        ApiService apiService = ApiClient.getClient().create(ApiService.class);
//        SignupRequest request = new SignupRequest(name, email, password, phoneNumber, gender);
//
//        apiService.registerUser(request).enqueue(new Callback<SignupResponse>() {
//            @Override
//            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    SignupResponse signupResponse = response.body();
//                    if (signupResponse.isSuccess()) {
//                        // Handle successful registration
//                        Toast.makeText(SignupActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        // Handle registration failure
//                        Toast.makeText(SignupActivity.this, signupResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(SignupActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SignupResponse> call, Throwable t) {
//                // Handle request failure (e.g., network error)
//                Toast.makeText(SignupActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
