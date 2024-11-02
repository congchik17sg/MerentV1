package com.example.mermentv1.ui.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mermentv1.R;

public class SettingActivity extends AppCompatActivity {

    private TextView txtUsername, txtEmail, txtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

//        txtUsername = findViewById(R.id.txt_username);
//        txtEmail = findViewById(R.id.txt_email);
//        txtAddress = findViewById(R.id.txt_address);

        // Get user details from intent
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String address = getIntent().getStringExtra("address");

        // Set user details to TextViews
        txtUsername.setText(username);
        txtEmail.setText(email);
        txtAddress.setText(address);
    }
    public void back(View view){
        finish();
    }
}