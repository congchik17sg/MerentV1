package com.example.mermentv1.ui.user;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mermentv1.R;
import com.example.mermentv1.model.Product;
import com.example.mermentv1.model.ProductResponse;
import com.example.mermentv1.ui.api.ApiService;
import com.example.mermentv1.ui.card.CardAdapter;
import com.example.mermentv1.ui.card.CardModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CameraRecorderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardModel> cardList;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_recoreder);

        recyclerView = findViewById(R.id.recycler_view);

//        init adpater;
//       CardAdapter cardAdapter = new CardAdapter(new ArrayList<>());

        // Set up RecyclerView with GridLayoutManager for 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Retrofit setup
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://merent.uydev.id.vn/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        fetchProducts();  // Fetch product data from API
//        addManualProducts();
    }

    // Fetch products from API
    private void fetchProducts() {
        Call<ProductResponse> call = apiService.getProducts();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body().getData();
                    if (products != null && !products.isEmpty()) {
                        Log.d("API_SUCCESS", "Fetched " + products.size() + " products.");
                        updateCardList(products);  // Call your method to update the RecyclerView or card list
                    } else {
                        // If the product list is null or empty, show a toast
                        Toast.makeText(CameraRecorderActivity.this, "No products available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful response
                    Log.e("API_ERROR", "Response unsuccessful, Code: " + response.code());
                    Log.e("API_ERROR", "Response message: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e("API_ERROR", "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // Optionally show a toast for failed request
                    Toast.makeText(CameraRecorderActivity.this, "Failed to retrieve products", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch data: " + t.getMessage());
            }
        });
    }

    // Update the card list with fetched products
    private void updateCardList(List<Product> products) {
        if (products != null && !products.isEmpty()) {
            cardList = new ArrayList<>();
            for (Product product : products) {
                cardList.add(new CardModel(
                        product.getName(),
                        "$" + product.getPrice(),
                        product.getUrlCenter(),  // Main image URL
                        product.getUrlLeft(),    // Left image URL
                        product.getUrlRight(),   // Right image URL
                        product.getUrlSide()     // Side image URL
                ));
            }
            cardAdapter = new CardAdapter(this, cardList);
            recyclerView.setAdapter(cardAdapter); // Ensure adapter is attached
            Log.d("RecyclerView", "Adapter attached with " + cardList.size() + " items.");
        } else {
            Log.d("RecyclerView", "No products to display.");
        }
    }

//    private void addManualProducts() {
//        cardList = new ArrayList<>();
//
//        // Example manually added products
//        cardList.add(new CardModel(
//                "Product 1",
//                "$50",
//                R.drawable.r5chinh, // Keep this as a string for URLs (if needed)
//                R.drawable.r5ben,   // Use as int for drawable resource
//                R.drawable.r5tren,  // Use as int for drawable resource
//                R.drawable.r5truoc  // Use as int for drawable resource
//        ));
//
//        cardList.add(new CardModel(
//                "Product 2",
//                "$75",
//                R.drawable.r100chinh, // Keep this as a string for URLs (if needed)
//                R.drawable.r100ben,   // Use as int for drawable resource
//                R.drawable.r5tren,    // Use as int for drawable resource
//                R.drawable.r5truoc    // Use as int for drawable resource
//        ));
//
//        cardAdapter = new CardAdapter(this, cardList);
//        recyclerView.setAdapter(cardAdapter);
//    }
}
