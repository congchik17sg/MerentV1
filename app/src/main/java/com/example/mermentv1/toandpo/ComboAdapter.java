package com.example.mermentv1.toandpo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mermentv1.R;
import com.example.mermentv1.model.ComboItem;
import com.example.mermentv1.ui.user.DetailComboActivity;

import java.util.List;

public class ComboAdapter extends RecyclerView.Adapter<ComboAdapter.ComboViewHolder> {
    private List<ComboItem> comboList;
    private Context context;

    public ComboAdapter(List<ComboItem> comboList, Context context) {
        this.comboList = comboList;
        this.context = context;
    }

    @Override
    public ComboViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.combo_item, parent, false);
        return new ComboViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComboViewHolder holder, int position) {
        ComboItem comboItem = comboList.get(position);
        holder.comboName.setText(comboItem.getComboName());
        holder.comboDescription.setText(comboItem.getDescription());
        Glide.with(context).load(comboItem.getUrlImg()).into(holder.comboImage);

        // Set the total price for this combo
        holder.totalPriceTextView.setText("Total Price: " + comboItem.getTotalPrice());

        // Display the products in the combo
        holder.productAdapter = new NewProductAdapter(context, comboItem.getProducts());
        holder.recyclerViewProducts.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        holder.recyclerViewProducts.setAdapter(holder.productAdapter);

        // Set click listener for the combo item
        holder.itemView.setOnClickListener(v -> {
            // Navigate to DetailComboActivity when combo item is clicked
            Intent intent = new Intent(context, DetailComboActivity.class);
            intent.putExtra("comboId", comboItem.getComboID());  // Passing the combo ID or whole ComboItem as extra
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return comboList.size();
    }

    public class ComboViewHolder extends RecyclerView.ViewHolder {
        TextView comboName, comboDescription, totalPriceTextView;
        ImageView comboImage;
        RecyclerView recyclerViewProducts;
        NewProductAdapter productAdapter;

        public ComboViewHolder(View itemView) {
            super(itemView);
            comboName = itemView.findViewById(R.id.comboName);
            comboDescription = itemView.findViewById(R.id.comboDescription);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);  // Initialize the totalPriceTextView
            comboImage = itemView.findViewById(R.id.comboImage);
            recyclerViewProducts = itemView.findViewById(R.id.recyclerViewProducts);
        }
    }
}
