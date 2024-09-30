package com.example.mermentv1.ui.user;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mermentv1.R;
import com.example.mermentv1.ui.card.CardAdapter;
import com.example.mermentv1.ui.card.CardModel;

import java.util.ArrayList;
import java.util.List;

public class CameraRecoreder extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardModel> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_recoreder);

        recyclerView = findViewById(R.id.recycler_view);

        // Set up RecyclerView with GridLayoutManager for 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Create card list and adapter
        cardList = new ArrayList<>();
        cardList.add(new CardModel("Item 1", "$100", R.drawable.baseline_add_24));
        cardList.add(new CardModel("Item 2", "$200", R.drawable.baseline_home_24));
        // Add more items...

        cardAdapter = new CardAdapter(this, cardList);
        recyclerView.setAdapter(cardAdapter);
    }
}