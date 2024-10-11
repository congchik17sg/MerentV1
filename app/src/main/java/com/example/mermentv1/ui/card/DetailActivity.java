package com.example.mermentv1.ui.card;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mermentv1.R;
import com.example.mermentv1.cart.CartManager;
import com.example.mermentv1.cart.CartModel;

public class DetailActivity extends AppCompatActivity {

    private TextView detailName, detailPrice;
    private ImageView detailImage, leftImage, rightImage, sideImage;
    private Button addToCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize the views
        detailName = findViewById(R.id.detail_name);
        detailPrice = findViewById(R.id.detail_price);
        detailImage = findViewById(R.id.detail_image);
        leftImage = findViewById(R.id.left_image);
        rightImage = findViewById(R.id.right_image);
        sideImage = findViewById(R.id.side_image);
        addToCartButton = findViewById(R.id.btn_add_to_cart);

        // Get data from intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("cardName");
        String price = intent.getStringExtra("cardPrice");
        String urlCenter = intent.getStringExtra("urlCenter");
        String urlLeft = intent.getStringExtra("urlLeft");
        String urlRight = intent.getStringExtra("urlRight");
        String urlSide = intent.getStringExtra("urlSide");

        // Set the data to views
        detailName.setText(name);
        detailPrice.setText(price);

        // Load images from URLs using Glide
        Glide.with(this).load(urlCenter).into(detailImage);
        Glide.with(this).load(urlLeft).into(leftImage);
        Glide.with(this).load(urlRight).into(rightImage);
        Glide.with(this).load(urlSide).into(sideImage);

        // Set click listeners to swap images
        leftImage.setOnClickListener(v -> {
            Glide.with(this).load(urlLeft).into(detailImage);
        });

        rightImage.setOnClickListener(v -> {
            Glide.with(this).load(urlRight).into(detailImage);
        });

        sideImage.setOnClickListener(v -> {
            Glide.with(this).load(urlSide).into(detailImage);
        });

        // Add to Cart button logic
        addToCartButton.setOnClickListener(v -> {
            // Pass the correct image URL instead of the ImageView
            CartManager.getInstance().addItem(new CartModel(name, urlCenter, 1));
        });
    }
}
