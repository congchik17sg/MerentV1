package com.example.mermentv1.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mermentv1.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<ProductItem> productItemList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    // Interface for item clicks
    public interface OnItemClickListener {
        void onItemClick(ProductItem productItem);
    }

    // Setter for the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public ProductAdapter(List<ProductItem> productItemList, Context context) {
        this.productItemList = productItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductItem productItem = productItemList.get(position);
        holder.nameTextView.setText(productItem.getName());

        // Format price with commas and append "VND"
        String formattedPrice = formatPriceWithVND(productItem.getPrice());
        holder.priceTextView.setText(formattedPrice);

        // Load product image using Glide
        Glide.with(context).load(productItem.getUrlCenter()).into(holder.imageView);

        // Handle item clicks
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(productItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productItemList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView priceTextView;
        ImageView imageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productName);
            priceTextView = itemView.findViewById(R.id.productPrice);
            imageView = itemView.findViewById(R.id.productImage);
        }
    }

    // Helper method to format the price with "VND" and commas
    private String formatPriceWithVND(double price) {
        // Format the price with commas for thousands
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        String formattedPrice = numberFormat.format(price);
        return formattedPrice + " VND";
    }
}
