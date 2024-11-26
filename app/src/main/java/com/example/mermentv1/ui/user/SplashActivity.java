package com.example.mermentv1.ui.user;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mermentv1.R;
import com.example.mermentv1.SigninActivity;
import com.example.mermentv1.SignupActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Find the small center image
        ImageView centerImage = findViewById(R.id.centerImage);

        // Create a fade-in animation
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(3000); // 3 seconds
        fadeIn.setFillAfter(true);
        centerImage.startAnimation(fadeIn);

        // Move to SigninActivity after 3 seconds
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, SigninActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}