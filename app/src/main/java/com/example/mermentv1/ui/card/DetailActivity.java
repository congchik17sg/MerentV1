package com.example.mermentv1.ui.card;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mermentv1.R;

public class DetailActivity extends AppCompatActivity {

    private TextView detailName, detailPrice;
    private ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize the views
        detailName = findViewById(R.id.detail_name);
        detailPrice = findViewById(R.id.detail_price);
        detailImage = findViewById(R.id.detail_image);

        // Get data from intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("cardName");
        String price = intent.getStringExtra("cardPrice");
        int imageResId = intent.getIntExtra("cardImage", 0);

        // Set the data to views
        detailName.setText(name);
        detailPrice.setText(price);
        detailImage.setImageResource(imageResId);
    }
}
