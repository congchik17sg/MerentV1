package com.example.mermentv1.ui.user;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mermentv1.R;
import com.example.mermentv1.cart.CartManager;
import com.example.mermentv1.cart.CartModel;
import com.example.mermentv1.model.ProductItem;
import com.example.mermentv1.model.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailComboActivity extends AppCompatActivity {

    private RecyclerView productsRecyclerView;
    private ProductAdapter productAdapter;
    private List<ProductItem> productList = new ArrayList<>();
    private TextView comboNameTextView;
    private Button addToCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_combo);

        // Initialize RecyclerView and Adapter
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the TextView for combo name (optional)
        comboNameTextView = findViewById(R.id.comboNameTextView);
        addToCartButton = findViewById(R.id.btn_add_to_cart);

        // Get the passed data from the intent
        int comboId = getIntent().getIntExtra("comboId", -1);
        List<ProductItem> comboProducts = (List<ProductItem>) getIntent().getSerializableExtra("comboProducts");

        if (comboId != -1 && comboProducts != null) {
            // Optionally set the combo name if it's passed
            comboNameTextView.setText("Combo: " + comboId);  // You can modify this to show the actual name

            // Use the passed products list to update the RecyclerView
            productList.clear();
            productList.addAll(comboProducts);
            productAdapter = new ProductAdapter(productList, this);
            productsRecyclerView.setAdapter(productAdapter);

            // Set add to cart button click listener
            addToCartButton.setOnClickListener(v -> {
                // Add the products to cart
                addProductsToCart(comboProducts);
            });

        } else {
            Toast.makeText(this, "Failed to load products", Toast.LENGTH_SHORT).show();
        }
    }

    // Add products to the cart
    private void addProductsToCart(List<ProductItem> comboProducts) {
        for (ProductItem product : comboProducts) {
            // Add each product to the cart using CartModel
            String imageUrl = product.getUrlCenter();  // Choose the image URL (e.g., urlCenter)
            String price = product.getPrice() + " VND";  // Append " VND" to the price

            CartModel cartModel = new CartModel(
                    product.getName(),         // Product name
                    imageUrl,                  // Product image URL (String)
                    1,                         // Default quantity is 1
                    price                      // Product price as String
            );

            // Add CartModel to cart (CartManager or CartData)
            CartManager.getInstance().addItem(cartModel);
        }

        Toast.makeText(this, "Products added to cart!", Toast.LENGTH_SHORT).show();
    }

}
