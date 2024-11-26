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
        cardList = new ArrayList<>();  // Initialize card list

        // Set up RecyclerView with GridLayoutManager for 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Initialize CardAdapter
        cardAdapter = new CardAdapter(this, cardList);
        recyclerView.setAdapter(cardAdapter);  // Attach the adapter to RecyclerView

        // Retrofit setup
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://merent.uydev.id.vn/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        fetchProducts();  // Fetch product data from API

//        TurnOffStatusBar();
    }

    // Fetch products with category "Camera" from the API
    private void fetchProducts() {
        Call<ProductResponse> call = apiService.getProductsByCategory("camera");  // Fetch products by category
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
                        Toast.makeText(CameraRecorderActivity.this, "No products available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful response
                    handleApiError(response);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch data: " + t.getMessage());
                Toast.makeText(CameraRecorderActivity.this, "Failed to retrieve products", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCardList(List<ProductItem> productItems) {
        if (productItems != null && !productItems.isEmpty()) {
            cardList = new ArrayList<>();

            // Filter products where productType equals "camera"
            for (ProductItem productItem : productItems) {
                if ("camera".equalsIgnoreCase(productItem.getProductType())) {
                    cardList.add(new CardModel(
                            productItem.getName(),
                             productItem.getPrice() + "VND",
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

    // Handle API error response
    private void handleApiError(Response<ProductResponse> response) {
        Log.e("API_ERROR", "Response unsuccessful, Code: " + response.code());
        Log.e("API_ERROR", "Response message: " + response.message());
        if (response.errorBody() != null) {
            try {
                Log.e("API_ERROR", "Error body: " + response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(CameraRecorderActivity.this, "Failed to retrieve products", Toast.LENGTH_SHORT).show();
    }

//    private void TurnOffStatusBar(){
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//    }

    // Update the card list with fetched products
//    private void updateCardList(List<Product> products) {
//        if (products != null && !products.isEmpty()) {
//            cardList.clear();  // Clear the current list
//            for (Product product : products) {
//                cardList.add(new CardModel(
//                        product.getName(),
//                        "$" + product.getPrice(),
//                        product.getUrlCenter(),  // Main image URL
//                        product.getUrlLeft(),    // Left image URL
//                        product.getUrlRight(),   // Right image URL
//                        product.getUrlSide()     // Side image URL
//                ));
//            }
//            cardAdapter.notifyDataSetChanged();  // Notify adapter of data change
//            Log.d("RecyclerView", "Adapter updated with " + cardList.size() + " items.");
//        } else {
//            Log.d("RecyclerView", "No products to display.");
//        }
//    }

}
