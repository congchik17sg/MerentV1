package com.example.mermentv1.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mermentv1.R;
import com.example.mermentv1.model.ComboItem;
import com.example.mermentv1.model.ComboProductAdapter;
import com.example.mermentv1.model.ProductAdapter;
import com.example.mermentv1.model.ProductItem;
import com.example.mermentv1.ui.api.ApiClient;
import com.example.mermentv1.ui.api.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailComboActivity extends AppCompatActivity {

    private TextView comboName, comboDescription;
    private ImageView comboImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_combo);

        // Initialize the views
        comboName = findViewById(R.id.comboName);
        comboDescription = findViewById(R.id.comboDescription);
        comboImage = findViewById(R.id.comboImage);

        // Retrieve the combo ID passed from ComboActivity
        Intent intent = getIntent();
        int comboId = intent.getIntExtra("comboId", -1);

        // Use the combo ID to fetch data or display details
        if (comboId != -1) {
            // Fetch combo details using the comboId (you can either fetch from the API or use static data)
            // For simplicity, assuming you fetch combo details from the API

            // Example of displaying static data based on comboId (you can replace with actual fetch logic)
            comboName.setText("Combo Name: Combo " + comboId);  // This should be replaced with actual combo data
            comboDescription.setText("Description of Combo " + comboId);
            Glide.with(this).load("https://example.com/combo_image.jpg").into(comboImage);
        }
    }
}
