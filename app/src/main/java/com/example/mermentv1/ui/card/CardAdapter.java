package com.example.mermentv1.ui.card;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // Import Glide
import com.example.mermentv1.R;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private Context context;
    private List<CardModel> cardList;

    public CardAdapter(Context context, List<CardModel> cardList) {
        this.context = context;
        this.cardList = cardList != null ? cardList : new ArrayList<>();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardModel cardModel = cardList.get(position);

        holder.cardName.setText(cardModel.getName());
        holder.cardPrice.setText(cardModel.getPrice());

        // Check if the image is a URL or a resource ID
        try {
            int imageResId = Integer.parseInt(cardModel.getImageUrl()); // Try to parse as an int
            // Load the drawable resource using Glide
            Glide.with(context)
                    .load(imageResId)
                    .into(holder.cardImage);
        } catch (NumberFormatException e) {
            // If it's not an int, load it as a URL
            Glide.with(context)
                    .load(cardModel.getImageUrl()) // Load as URL
                    .into(holder.cardImage);
        }

        // OnClickListener to open DetailActivity with the selected card details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);

            // Pass name, price, and all image URLs to DetailActivity
            intent.putExtra("cardName", cardModel.getName());
            intent.putExtra("cardPrice", cardModel.getPrice());
            intent.putExtra("cardDescription", cardModel.getDescription()); // Pass description")

            if (cardModel.getImageUrl().startsWith("http")) {
                // It's a URL, pass as string
                intent.putExtra("urlCenter", cardModel.getImageUrl());
                intent.putExtra("urlLeft", cardModel.getUrlLeft());
                intent.putExtra("urlRight", cardModel.getUrlRight());
                intent.putExtra("urlSide", cardModel.getUrlSide());
            } else {
                // It's a drawable resource, pass as int
                intent.putExtra("urlCenter", Integer.parseInt(cardModel.getImageUrl()));
                intent.putExtra("urlLeft", Integer.parseInt(cardModel.getUrlLeft()));
                intent.putExtra("urlRight", Integer.parseInt(cardModel.getUrlRight()));
                intent.putExtra("urlSide", Integer.parseInt(cardModel.getUrlSide()));
            }

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    // Method to update card data
    public void updateData(List<CardModel> newCardList) {
        this.cardList.clear();
        this.cardList.addAll(newCardList);
        notifyDataSetChanged();
    }

    // ViewHolder class for card items
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView cardName, cardPrice;
        public ImageView cardImage;
        public CardView cardView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardName = itemView.findViewById(R.id.card_name);
            cardPrice = itemView.findViewById(R.id.card_price);
            cardImage = itemView.findViewById(R.id.card_image);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }


}
