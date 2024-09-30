package com.example.mermentv1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mermentv1.ui.api.ApiService;
import com.example.mermentv1.model.SigninRequest;
import com.example.mermentv1.model.SigninResponse;
import com.example.mermentv1.ui.api.UnsafeHttpClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        // Find the btn_signup TextView
        TextView btnSignup = findViewById(R.id.btn_signup);

        OkHttpClient unsafeHttpClient = UnsafeHttpClient.getUnsafeOkHttpClient();

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(unsafeHttpClient)
                .baseUrl("https://10.0.2.2:7253/")  // Adjust to your actual base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

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
                if (response.isSuccessful()) {
                    SigninResponse signinResponse = response.body();
                    String username = signinResponse.getUsername();  // Assuming getUsername() exists in SigninResponse

                    // Handle successful response (e.g., save token)
                    Toast.makeText(SigninActivity.this, "Login successful: " + signinResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    // Pass the username to MainActivity
                    Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    finish();

                } else {
                    // Handle API error response
                    Toast.makeText(SigninActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SigninResponse> call, Throwable t) {
                // Handle connection failure
                Toast.makeText(SigninActivity.this, "Connection failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
