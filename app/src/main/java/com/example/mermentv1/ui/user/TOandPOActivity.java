package com.example.mermentv1.ui.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mermentv1.R;
import com.example.mermentv1.model.ProductOrder;
import com.example.mermentv1.model.ProductOrderResponse;
import com.example.mermentv1.model.Transaction;
import com.example.mermentv1.model.TransactionResponse;
import com.example.mermentv1.toandpo.ProductOrderAdapter;
import com.example.mermentv1.toandpo.TransactionAdapter;
import com.example.mermentv1.ui.api.ApiClient;
import com.example.mermentv1.ui.api.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TOandPOActivity extends AppCompatActivity {

    private int currentPage = 1, currentOrderPage = 1;
    private static final int PAGE_SIZE = 5;

    private final List<Transaction> allTransactions = new ArrayList<>();
    private final List<Transaction> paginatedTransactions = new ArrayList<>();
    private final List<ProductOrder> allProductOrders = new ArrayList<>();
    private final List<ProductOrder> paginatedProductOrders = new ArrayList<>();

    private TransactionAdapter transactionAdapter;
    private ProductOrderAdapter productOrderAdapter;

    private TextView tvPageNumber, tvOrderPageNumber;
    private Button btnPreviousPage, btnNextPage, btnPreviousOrderPage, btnNextOrderPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toandpo);

        // Setup RecyclerView for Transactions
        RecyclerView recyclerViewTransactions = findViewById(R.id.recyclerViewTransactions);
        recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(this));
        transactionAdapter = new TransactionAdapter(paginatedTransactions);
        recyclerViewTransactions.setAdapter(transactionAdapter);

        // Setup RecyclerView for Product Orders
        RecyclerView recyclerViewProductOrders = findViewById(R.id.recyclerViewOrders);
        recyclerViewProductOrders.setLayoutManager(new LinearLayoutManager(this));
        productOrderAdapter = new ProductOrderAdapter(paginatedProductOrders);
        recyclerViewProductOrders.setAdapter(productOrderAdapter);

        // Initialize UI elements
        tvPageNumber = findViewById(R.id.tvPageNumber);
        tvOrderPageNumber = findViewById(R.id.tvOrderPageNumber);
        btnPreviousPage = findViewById(R.id.btnPreviousPage);
        btnNextPage = findViewById(R.id.btnNextPage);
        btnPreviousOrderPage = findViewById(R.id.btnPreviousOrderPage);
        btnNextOrderPage = findViewById(R.id.btnNextOrderPage);

        // Setup transaction pagination buttons
        btnPreviousPage.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                loadPage(currentPage);
            }
        });

        btnNextPage.setOnClickListener(v -> {
            if ((currentPage * PAGE_SIZE) < allTransactions.size()) {
                currentPage++;
                loadPage(currentPage);
            }
        });

        // Setup product order pagination buttons
        btnPreviousOrderPage.setOnClickListener(v -> {
            if (currentOrderPage > 1) {
                currentOrderPage--;
                loadOrderPage(currentOrderPage);
            }
        });

        btnNextOrderPage.setOnClickListener(v -> {
            if ((currentOrderPage * PAGE_SIZE) < allProductOrders.size()) {
                currentOrderPage++;
                loadOrderPage(currentOrderPage);
            }
        });

        // Fetch data
        fetchTransactions();
        fetchProductOrders();
    }

    private void fetchTransactions() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        if (token == null) {
            Toast.makeText(this, "Token not found. Please login again.", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<TransactionResponse> call = ApiClient.getClient(this)
                .create(ApiService.class)
                .getUserTransactions("Bearer " + token, "PERSONAL", 1, Integer.MAX_VALUE);

        call.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(@NonNull Call<TransactionResponse> call, @NonNull Response<TransactionResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    allTransactions.clear();
                    allTransactions.addAll(response.body().getData());
                    loadPage(currentPage);
                } else {
                    Toast.makeText(TOandPOActivity.this, "Failed to fetch transactions.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TransactionResponse> call, @NonNull Throwable t) {
                Toast.makeText(TOandPOActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchProductOrders() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        if (token == null) {
            Toast.makeText(this, "Token not found. Please login again.", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<List<ProductOrder>> call = ApiClient.getClient(this)
                .create(ApiService.class)
                .getProductOrdersByUser(1);

        call.enqueue(new Callback<List<ProductOrder>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductOrder>> call, @NonNull Response<List<ProductOrder>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allProductOrders.clear();
                    allProductOrders.addAll(response.body());
                    loadOrderPage(currentOrderPage);
                } else {
                    Toast.makeText(TOandPOActivity.this, "Failed to fetch product orders.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductOrder>> call, @NonNull Throwable t) {
                Toast.makeText(TOandPOActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPage(int page) {
        int start = (page - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, allTransactions.size());

        paginatedTransactions.clear();
        if (start < allTransactions.size()) {
            paginatedTransactions.addAll(allTransactions.subList(start, end));
        }
        transactionAdapter.notifyDataSetChanged();

        tvPageNumber.setText(String.format("Page %d of %d", page, (allTransactions.size() + PAGE_SIZE - 1) / PAGE_SIZE));
        btnPreviousPage.setEnabled(page > 1);
        btnNextPage.setEnabled(end < allTransactions.size());
    }

    private void loadOrderPage(int page) {
        int start = (page - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, allProductOrders.size());

        paginatedProductOrders.clear();
        if (start < allProductOrders.size()) {
            paginatedProductOrders.addAll(allProductOrders.subList(start, end));
        }
        productOrderAdapter.notifyDataSetChanged();

        tvOrderPageNumber.setText(String.format("Page %d of %d", page, (allProductOrders.size() + PAGE_SIZE - 1) / PAGE_SIZE));
        btnPreviousOrderPage.setEnabled(page > 1);
        btnNextOrderPage.setEnabled(end < allProductOrders.size());
    }

}
