package com.example.mermentv1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mermentv1.ui.api.ApiClient;
import com.example.mermentv1.ui.api.ApiService;
import com.example.mermentv1.model.SigninRequest;
import com.example.mermentv1.model.SigninResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {

    private ApiService apiService;
    private EditText edtEmail, edtPassword;
    private Button btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        // Initialize the views
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnSignin = findViewById(R.id.btn_signin);
        TextView btnSignup = findViewById(R.id.btn_signup);

        // Set the API client
        apiService = ApiClient.getClient(SigninActivity.this).create(ApiService.class);


        // Set login button click listener
        btnSignin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (validateInput(email, password)) {
                loginUser(email, password);  // Perform login if input is valid
            }
        });

        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    private void storeToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void loginUser(String email, String password) {
        SigninRequest loginRequest = new SigninRequest(email, password);

        // Make the API call
        Call<SigninResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<SigninResponse>() {
            @Override
            public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SigninResponse signinResponse = response.body();
                    String name = signinResponse.getName();
                    String token = signinResponse.getToken(); // Get the token from the response

                    // Handle successful response
                    Toast.makeText(SigninActivity.this, "Login successful: " + signinResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    // Store the token for future use
                    storeToken(token);

                    // Pass the username to MainActivity
                    Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                    intent.putExtra("name", name);
                    startActivity(intent);
                    finish();
                } else {
                    // Log the error response
                    try {
                        String errorResponse = response.errorBody().string();
                        Log.e("RetrofitError", "Error: " + errorResponse);
                        Toast.makeText(SigninActivity.this, "Login failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SigninResponse> call, Throwable t) {
                Log.e("RetrofitError", "Error: " + t.getMessage(), t);
                Toast.makeText(SigninActivity.this, "Connection failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
