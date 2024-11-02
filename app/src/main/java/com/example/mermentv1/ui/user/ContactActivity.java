package com.example.mermentv1.ui.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mermentv1.R;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact); // Ensure this is the correct XML layout file

        ImageView facebookIcon = findViewById(R.id.facebook);
        ImageView instagramIcon = findViewById(R.id.instagram);

        // Set up onClick listener for Facebook
        facebookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facebookUrl = "https://www.facebook.com/profile.php?id=61565922489145"; // Replace with your actual URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
                startActivity(intent);
            }
        });

        // Set up onClick listener for Instagram
        instagramIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String instagramUrl = "https://www.instagram.com/yourinstagrampage"; // Replace with your actual URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl));
                startActivity(intent);
            }
        });
    }

    // Back button functionality (optional)
    public void back(View view) {
        onBackPressed();  // You can customize this as needed
    }
}