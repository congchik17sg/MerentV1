package com.example.mermentv1.ui.card;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mermentv1.R;

public class DetailActivity extends AppCompatActivity {

    private TextView detailName, detailPrice, detailDescription; // Add detailDescription here
    private ImageView detailImage2 , detailImage, leftImage, rightImage, sideImage;
    private Button addToCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize the views
        detailName = findViewById(R.id.detail_name);
        detailPrice = findViewById(R.id.detail_price);
        detailImage = findViewById(R.id.detail_image);
        detailImage2 = findViewById(R.id.detail_image2);
        leftImage = findViewById(R.id.left_image);
        rightImage = findViewById(R.id.right_image);
        sideImage = findViewById(R.id.side_image);
        addToCartButton = findViewById(R.id.btn_add_to_cart);
        detailDescription = findViewById(R.id.detail_description); // Initialize the description TextView

        // Get data from intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("cardName");
        String price = intent.getStringExtra("cardPrice");
        String description = intent.getStringExtra("cardDescription"); // Get description from intent
        String urlCenter = intent.getStringExtra("urlCenter");
        String urlLeft = intent.getStringExtra("urlLeft");
        String urlRight = intent.getStringExtra("urlRight");
        String urlSide = intent.getStringExtra("urlSide");

        // Set the data to views
        detailName.setText(name);
        detailPrice.setText(price);
        detailDescription.setText(description); // Set description to TextView

        // Load images from URLs using Glide
        Glide.with(this).load(urlCenter).into(detailImage);
        Glide.with(this).load(urlCenter).into(detailImage2);
        Glide.with(this).load(urlLeft).into(leftImage);
        Glide.with(this).load(urlRight).into(rightImage);
        Glide.with(this).load(urlSide).into(sideImage);

        // Set click listeners to swap images
        detailImage2.setOnClickListener(v -> {
            Glide.with(this).load(urlCenter).into(detailImage);
        });
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
            CartManager cartManager = CartManager.getInstance();

            // Check if the product is already in the cart
            if (cartManager.isProductInCart(name)) {
                // Show a toast if the product is already added
                Toast.makeText(DetailActivity.this, "Product already added to cart", Toast.LENGTH_SHORT).show();
            } else {
                // Add the product to the cart
                cartManager.addItem(new CartModel(name, urlCenter, 1 , price));
                Toast.makeText(DetailActivity.this, "Product added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
