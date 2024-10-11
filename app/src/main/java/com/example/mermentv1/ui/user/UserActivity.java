package com.example.mermentv1.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mermentv1.R;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);

        // Set up window insets for padding
        setupWindowInsets();

        // Display the user's name
        displayUserName();

        // Set up the navigation click listeners
        setupNavigation();
    }

    // Function to handle window insets for system bars
    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Function to retrieve and display the user's name
    private void displayUserName() {
        TextView textView = findViewById(R.id.name);

        // Retrieve the username from the Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name"); // Get the username

        // Set the username to the TextView or default to "Guest"
        if (name != null) {
            textView.setText(name);
        } else {
            textView.setText("Guest");
        }
    }

    // Function to set up the click listeners for navigation
    private void setupNavigation() {
        // "Thanh Toán" click listener (Navigates to PaymentActivity)
        LinearLayout thanhToanLayout = findViewById(R.id.thanh_toan_layout);
        thanhToanLayout.setOnClickListener(v -> navigateToActivity(PaymentActivity.class));

        // "Địa Chỉ" click listener (Navigates to AddressActivity)
        LinearLayout diaChiLayout = findViewById(R.id.dia_chi_layout);
        diaChiLayout.setOnClickListener(v -> navigateToActivity(AddressActivity.class));

        // "Cài Đặt" click listener (Navigates to SettingActivity)
        LinearLayout caiDatLayout = findViewById(R.id.cai_dat_layout);
        caiDatLayout.setOnClickListener(v -> navigateToActivity(SettingActivity.class));
    }

    // Utility function to navigate to the specified activity
    private void navigateToActivity(Class<?> targetActivity) {
        Intent intent = new Intent(UserActivity.this, targetActivity);
        startActivity(intent);
    }
}
