package com.example.mermentv1.ui.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mermentv1.R;
import com.example.mermentv1.cart.CartAdapter;
import com.example.mermentv1.cart.CartManager;
import com.example.mermentv1.cart.CartModel;
import com.example.mermentv1.cart.SpacingItemDecoration;
import com.example.mermentv1.model.PaymentRequest;
import com.example.mermentv1.model.PaymentResponse;
import com.example.mermentv1.ui.api.ApiClient;
import com.example.mermentv1.ui.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartActivity extends AppCompatActivity implements CartAdapter.OnQuantityChangeListener {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartModel> cartList;
    private Button thanhToanButton;
    private TextView totalAmountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        recyclerView = findViewById(R.id.recycler_view);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing);
        recyclerView.addItemDecoration(new SpacingItemDecoration(spacingInPixels));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get cart items from CartManager
        cartList = CartManager.getInstance().getCartItems();

        // Debug log to verify cart data
        for (CartModel item : cartList) {
            Log.d("CartItem", "Name: " + item.getName() + ", Price: " + item.getPrice() + ", Quantity: " + item.getQuantity());
        }

        // Set up the adapter
        cartAdapter = new CartAdapter(this, cartList, this);
        recyclerView.setAdapter(cartAdapter);

        // Initialize total amount TextView and "Thanh Toán" button
        totalAmountTextView = findViewById(R.id.tv_total_amount);
        thanhToanButton = findViewById(R.id.btn_thanh_toan);

        // Set click listener for "Thanh Toán" button
        thanhToanButton.setOnClickListener(v -> {
            if (cartList.isEmpty()) {
                Toast.makeText(this, "Cart is empty. Add items to proceed.", Toast.LENGTH_SHORT).show();
            } else {
                // Calculate total amount and initiate payment link creation
                double totalAmount = calculateTotalAmount();
                createPaymentLink((int) totalAmount); // Cast to int
            }
        });

        // Update total amount on create
        updateTotalAmount();
    }

    // Calculate total amount and update TextView
    private void updateTotalAmount() {
        double totalAmount = calculateTotalAmount();
        totalAmountTextView.setText("Total: " + totalAmount + " VND");
    }

    // Calculate total amount based on items in cart
    private double calculateTotalAmount() {
        double totalAmount = 0;
        for (CartModel cartModel : cartList) {
            String priceString = cartModel.getPrice();
            if (priceString != null) {
                try {
                    // Remove non-numeric characters from the price string and parse it
                    priceString = priceString.replaceAll("[^\\d.]", "");
                    totalAmount += cartModel.getQuantity() * Double.parseDouble(priceString);
                } catch (NumberFormatException e) {
                    Log.e("ShoppingCart", "Invalid price format for item: " + cartModel.getName());
                }
            } else {
                Log.e("ShoppingCart", "Price is null for item: " + cartModel.getName());
            }
        }
        return totalAmount;
    }

    // Trigger payment link creation via API call
    private void createPaymentLink(int totalAmount) {
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("token", null);

        if (authToken == null) {
            Toast.makeText(this, "Authentication token is missing. Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }

        PaymentRequest paymentRequest = new PaymentRequest(totalAmount);
        Log.d("PaymentRequest", "Requesting payment link with amount: " + totalAmount);

        apiService.createPaymentLink("Bearer " + authToken, paymentRequest).enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    String paymentLink = response.body().getData();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(paymentLink));
                    startActivity(browserIntent);
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                Log.e("API Failure", "Error occurred: " + t.getMessage());
                Toast.makeText(ShoppingCartActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleErrorResponse(Response<PaymentResponse> response) {
        Log.e("PaymentError", "Error code: " + response.code() + ", message: " + response.message());
        try {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
            Log.e("PaymentError", "Error body: " + errorBody);
        } catch (Exception e) {
            Log.e("PaymentError", "Failed to read error body", e);
        }
        Toast.makeText(ShoppingCartActivity.this, "Failed to create payment link.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onQuantityChanged(CartModel cartModel, int newQuantity) {
        if (newQuantity <= 0) {
            // If the item is removed, update the cartList
            cartList.remove(cartModel);
            cartAdapter.notifyDataSetChanged(); // Notify adapter about the change
            Toast.makeText(this, "Product removed from cart.", Toast.LENGTH_SHORT).show();
        } else {
            // Update the quantity if the new value is valid
            cartModel.setQuantity(newQuantity);
            cartAdapter.notifyDataSetChanged();
        }

        // Update the total amount whenever the quantity changes
        updateTotalAmount();
    }

    @Override
    public void onItemRemoved(CartModel cartModel) {
        cartList.remove(cartModel);
        cartAdapter.notifyDataSetChanged(); // Notify the adapter about the removal
        Toast.makeText(this, "Product removed from cart.", Toast.LENGTH_SHORT).show();
        updateTotalAmount(); // Update total after removal
    }
}
