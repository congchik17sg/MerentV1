package com.example.mermentv1.ui.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mermentv1.R;
import com.example.mermentv1.model.UserResponse;
import com.example.mermentv1.ui.api.ApiClient;
import com.example.mermentv1.ui.api.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {

    private ApiService apiService;
    private TextView txtUserID, txtOrderID;
    private EditText edtUsername, edtEmail, edtPhone, edtGender;
    private Button btnBack, btnUpdate;

    private int existingRoleID; // To store roleID from fetched data
    private boolean existingIsConfirmed; // To store isConfirmed from fetched data

    private String existingPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        // Initialize views
        edtUsername = findViewById(R.id.edt_username);
        edtEmail = findViewById(R.id.edt_email);
        edtPhone = findViewById(R.id.edt_phone);
        edtGender = findViewById(R.id.edt_gender);
        txtUserID = findViewById(R.id.txt_usr_id);
        txtOrderID = findViewById(R.id.txt_order_id);
        btnBack = findViewById(R.id.btn_back);
        btnUpdate = findViewById(R.id.btn_update);

        // Initialize API service
        apiService = ApiClient.getClient(UserInfoActivity.this).create(ApiService.class);

        // Retrieve token from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        if (token != null) {
            fetchUserDetails(token);
        } else {
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show();
        }

        // Back button listener
        btnBack.setOnClickListener(v -> finish());

        // Update button listener
        btnUpdate.setOnClickListener(v -> {
            String updatedUsername = edtUsername.getText().toString();
            String updatedEmail = edtEmail.getText().toString();
            String updatedPhone = edtPhone.getText().toString();
            String updatedGender = edtGender.getText().toString();

            if (isValidInput(updatedUsername, updatedEmail, updatedPhone, updatedGender)) {
                updateUserDetails(updatedUsername, updatedEmail, updatedPhone, updatedGender, token);
            } else {
                Toast.makeText(UserInfoActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserDetails(String token) {
        Call<UserResponse> call = apiService.getUserDetails("Bearer " + token);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();
                    UserResponse.UserData userData = userResponse.getData();

                    // Populate UI with user details
                    edtUsername.setText(userData.getName());
                    edtEmail.setText(userData.getEmail());
                    edtPhone.setText(userData.getPhoneNumber());
                    edtGender.setText(userData.getGender());
                    txtUserID.setText(String.valueOf(userData.getId()));
                    txtOrderID.setText(String.valueOf(userData.getRoleID()));

                    // Save existing values for roleID and isConfirmed
                    existingRoleID = userData.getRoleID();
                    existingIsConfirmed = userData.isConfirmed();
                    existingPassword = userData.getPassword();
                } else {
                    Toast.makeText(UserInfoActivity.this, "Failed to fetch user details.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(UserInfoActivity.this, "Connection failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserDetails(String name, String email, String phone, String gender, String token) {
        int userId = Integer.parseInt(txtUserID.getText().toString()); // Retrieve user ID from TextView

        // Prepare the updated user data
        UserResponse.UserData updatedData = new UserResponse.UserData();
        updatedData.setName(name);
        updatedData.setEmail(email);
        updatedData.setPhoneNumber(phone);
        updatedData.setGender(gender);
        updatedData.setRoleID(existingRoleID); // Preserve existing roleID
        updatedData.setConfirmed(existingIsConfirmed); // Preserve existing isConfirmed

        // Make sure the password is set correctly
        if (existingPassword != null) {
            updatedData.setPassword(existingPassword);
        } else {
            // If existingPassword is null, you might need to handle this case appropriately, e.g., by leaving it as is.
            updatedData.setPassword(null); // Or omit it, depending on your backend logic
        }

        // Log the data being sent to the server
        Log.d("UpdateRequest", "Updating user with data: " + updatedData.toString());

        Call<Void> call = apiService.updateUserInfo("Bearer " + token, userId, updatedData);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserInfoActivity.this, "User details updated successfully.", Toast.LENGTH_SHORT).show();
                    fetchUserDetails(token); // Refresh user details
                } else {
                    try {
                        // Log the HTTP status code and error response body
                        Log.e("UpdateError", "HTTP Code: " + response.code());
                        String errorBody = response.errorBody().string();
                        Log.e("UpdateError", "Error Response: " + errorBody);
                    } catch (Exception e) {
                        Log.e("UpdateError", "Error parsing errorBody", e);
                    }
                    Toast.makeText(UserInfoActivity.this, "Failed to update user details.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Log failure details
                Log.e("UpdateFailure", "Request failed: " + t.getMessage());
                Toast.makeText(UserInfoActivity.this, "Connection failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private boolean isValidInput(String name, String email, String phone, String gender) {
        return !name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !gender.isEmpty();
    }
}
