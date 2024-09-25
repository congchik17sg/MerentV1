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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private ApiService apiService;
    private EditText edtUsername, edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Navigate to SigninActivity when clicking the sign-in text
        TextView btnSignIn = findViewById(R.id.btn_signin);
        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
            startActivity(intent);
        });

        // Initialize the API service
        apiService = ApiClient.getClient(SignupActivity.this).create(ApiService.class);

        // Initialize views
        edtUsername = findViewById(R.id.edt_username);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        Button btnSignup = findViewById(R.id.btn_signup);

        // Handle user registration when clicking the signup button
        btnSignup.setOnClickListener(v -> {
            String name = edtUsername.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String phoneNumber = "123456789"; // Example, or add EditText for user input
            String gender = "male";  // Example, or add a spinner/selection for gender

            if (validateInputs(name, email, password)) {
                registerUser(name, email, password, phoneNumber, gender);  // Call registration method if valid
            } else {
                Toast.makeText(SignupActivity.this, "Please check your input fields", Toast.LENGTH_SHORT).show();
            }
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

    /* Register user using the API */
    private void registerUser(String name, String email, String password, String phoneNumber, String gender) {
        SignupRequest request = new SignupRequest(name, email, password, phoneNumber, gender);

        apiService.registerUser(request).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SignupResponse signupResponse = response.body();
                    if (signupResponse.isSuccess()) {
                        // Handle successful registration
                        Toast.makeText(SignupActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                        // Redirect to SigninActivity after registration
                        Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                        startActivity(intent);
                        finish();  // Optional: close the signup activity
                    } else {
                        // Handle registration failure
                        Toast.makeText(SignupActivity.this, signupResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                // Handle request failure (e.g., network error)
                Toast.makeText(SignupActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
