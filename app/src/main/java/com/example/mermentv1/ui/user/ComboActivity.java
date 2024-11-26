package com.example.mermentv1.ui.user;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mermentv1.R;
import com.example.mermentv1.model.ComboItem;
import com.example.mermentv1.model.ComboResponse;
import com.example.mermentv1.ui.api.ApiClient;
import com.example.mermentv1.ui.api.ApiService;
import com.example.mermentv1.toandpo.ComboAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComboActivity extends AppCompatActivity {

    private RecyclerView comboRecyclerView;
    private ComboAdapter comboAdapter;
    private List<ComboItem> comboList = new ArrayList<>();
//    private TextView totalPriceTextView;  // Add TextView for total price

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo);

        // Initialize RecyclerView and Adapter
        comboRecyclerView = findViewById(R.id.comboRecyclerView);
        comboRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with an empty list
        comboAdapter = new ComboAdapter(comboList, this);
        comboRecyclerView.setAdapter(comboAdapter);

        // Initialize the TextView for total price


        // Fetch combo data from API
        fetchComboData();
    }

    private void fetchComboData() {
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        // Call API to fetch combo products
        Call<ComboResponse> call = apiService.getComboProducts();
        call.enqueue(new Callback<ComboResponse>() {
            @Override
            public void onResponse(Call<ComboResponse> call, Response<ComboResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Log the entire response body
                    Log.d("ComboActivity", "Response body: " + response.body().toString());

                    List<ComboItem> combos = response.body().getData();
                    if (combos != null) {
                        Log.d("ComboActivity", "Combos list size: " + combos.size()); // Log the size of the list
                        comboList.clear();
                        comboList.addAll(combos); // Only add if the list is not null
                        comboAdapter.notifyDataSetChanged(); // Notify adapter to update UI

                        // Calculate and display total price
//                        updateTotalPrice();

                        Toast.makeText(ComboActivity.this, "Data loaded successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("ComboActivity", "Combos list is null");
                        Toast.makeText(ComboActivity.this, "No combos found.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("ComboActivity", "Failed to load data: " + response.code());
                    Toast.makeText(ComboActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ComboResponse> call, Throwable t) {
                Log.e("ComboActivity", "API call failed", t);
                Toast.makeText(ComboActivity.this, "Failed to fetch data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to calculate total price
//    private void updateTotalPrice() {
//        int totalPrice = 0;
//
//        // Iterate through comboList to calculate total price
//        for (ComboItem comboItem : comboList) {
//            totalPrice += comboItem.getTotalPrice();  // Use the totalPrice from ComboItem
//        }
//
//        // Update the TextView to display total price
//        totalPriceTextView.setText("Total Price: " + totalPrice);
//    }




}
