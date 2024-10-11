package com.example.mermentv1.ui.user;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mermentv1.R;
import com.example.mermentv1.cart.CartAdapter;
import com.example.mermentv1.cart.CartManager;
import com.example.mermentv1.cart.CartModel;

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartModel> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get cart items from CartManager
        cartList = CartManager.getInstance().getCartItems();

        // Set up the adapter
        cartAdapter = new CartAdapter(this, cartList);
        recyclerView.setAdapter(cartAdapter);
    }
}