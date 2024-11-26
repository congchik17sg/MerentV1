package com.example.mermentv1.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mermentv1.R;
import com.example.mermentv1.model.Product;
import com.example.mermentv1.model.ProductAdapter;
import com.example.mermentv1.model.ProductResponse;
import com.example.mermentv1.ui.api.ApiClient;
import com.example.mermentv1.ui.api.ApiService;
import com.example.mermentv1.ui.card.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> allProducts = new ArrayList<>();
    private List<Product> filteredProducts = new ArrayList<>();
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);

        // Initialize ApiService
        apiService = ApiClient.getClient(this).create(ApiService.class);

        // Initialize adapter
        productAdapter = new ProductAdapter(filteredProducts, this);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set item click listener
        productAdapter.setOnItemClickListener(product -> {
            Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
            intent.putExtra("cardName", product.getName());
//            intent.putExtra("cardPrice", product.getPrice());
            intent.putExtra("cardPrice", String.valueOf(product.getPrice()) + " VND");
            intent.putExtra("cardDescription", product.getDescription());
            intent.putExtra("urlCenter", product.getUrlCenter());
            intent.putExtra("urlLeft", product.getUrlLeft());
            intent.putExtra("urlRight", product.getUrlRight());
            intent.putExtra("urlSide", product.getUrlSide());
            startActivity(intent);
        });

        // Fetch products
        fetchProducts();

        // SearchView listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterProducts(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProducts(newText);
                return false;
            }
        });
    }

    private void fetchProducts() {
        Log.d("API_FETCH", "Fetching all products...");
        apiService.getAllProducts().enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body().getData();
                    if (products != null && !products.isEmpty()) {
                        allProducts.clear();
                        allProducts.addAll(products);
                        filteredProducts.clear();
                        filteredProducts.addAll(products);
                        productAdapter.notifyDataSetChanged();
                        Log.d("API_SUCCESS", "Fetched " + products.size() + " products.");
                    } else {
                        Toast.makeText(SearchActivity.this, "No products available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SearchActivity.this, "Failed to retrieve products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch data: " + t.getMessage());
                Toast.makeText(SearchActivity.this, "Failed to retrieve products", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterProducts(String query) {
        filteredProducts.clear();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        productAdapter.notifyDataSetChanged();
    }
}
