package com.example.mermentv1.ui.user;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mermentv1.R;
import com.example.mermentv1.model.Product;
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

public class LensActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardModel> cardList;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lens); // Create this layout file

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
        Call<ProductResponse> call = apiService.getProductsByCategory("lens");  // Fetch products by category
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body().getData();
                    if (products != null && !products.isEmpty()) {
                        Log.d("API_SUCCESS", "Fetched " + products.size() + " camera products.");
                        updateCardList(products);  // Update the RecyclerView or card list
                    } else {
                        // Show a toast if the product list is empty
                        Toast.makeText(LensActivity.this, "No products available", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch data: " + t.getMessage());
                Toast.makeText(LensActivity.this, "Failed to retrieve products", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCardList(List<Product> products) {
        if (products != null && !products.isEmpty()) {
            cardList = new ArrayList<>();

            // Filter products where productType equals "camera"
            for (Product product : products) {
                if ("lens".equalsIgnoreCase(product.getProductType())) {
                    cardList.add(new CardModel(
                            product.getName(),
                            product.getPrice() +"VND"  ,
                            product.getDescription(),
                            product.getUrlCenter(),  // Main image URL
                            product.getUrlLeft(),    // Left image URL
                            product.getUrlRight(),   // Right image URL
                            product.getUrlSide()     // Side image URL
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