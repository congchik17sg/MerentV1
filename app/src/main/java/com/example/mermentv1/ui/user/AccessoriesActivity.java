package com.example.mermentv1.ui.user;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mermentv1.R;
import com.example.mermentv1.model.ProductItem;
import com.example.mermentv1.model.ProductResponse;
import com.example.mermentv1.ui.api.ApiService;
import com.example.mermentv1.ui.card.CardAdapter;
import com.example.mermentv1.ui.card.CardModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccessoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardModel> cardList;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessories); // Create this layout file

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://merent.uydev.id.vn/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        fetchProducts();
    }

    private void fetchProducts() {
        Call<ProductResponse> call = apiService.getProductsByCategory("accessories");  // Fetch products by category
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductItem> productItems = response.body().getData();
                    if (productItems != null && !productItems.isEmpty()) {
                        Log.d("API_SUCCESS", "Fetched " + productItems.size() + " camera products.");
                        updateCardList(productItems);  // Update the RecyclerView or card list
                    } else {
                        // Show a toast if the product list is empty
                        Toast.makeText(AccessoriesActivity.this, "No products available", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch data: " + t.getMessage());
                Toast.makeText(AccessoriesActivity.this, "Failed to retrieve products", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCardList(List<ProductItem> productItems) {
        if (productItems != null && !productItems.isEmpty()) {
            cardList = new ArrayList<>();

            // Filter products where productType equals "camera"
            for (ProductItem productItem : productItems) {
                if ("accessories".equalsIgnoreCase(productItem.getProductType())) {
                    cardList.add(new CardModel(
                            productItem.getName(),
                            productItem.getPrice() +"VND"  ,
                            productItem.getDescription(),
                            productItem.getUrlCenter(),  // Main image URL
                            productItem.getUrlLeft(),    // Left image URL
                            productItem.getUrlRight(),   // Right image URL
                            productItem.getUrlSide()     // Side image URL
                    ));
                }
            }

            // Check if any camera products were added to the list
            if (!cardList.isEmpty()) {
                cardAdapter = new CardAdapter(this, cardList);
                recyclerView.setAdapter(cardAdapter); // Ensure adapter is attached
                Log.d("RecyclerView", "Adapter attached with " + cardList.size() + " camera items.");
            } else {
                Log.d("RecyclerView", "No camera products to display.");
                Toast.makeText(this, "No camera products available", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("RecyclerView", "No products to display.");
        }
    }
}