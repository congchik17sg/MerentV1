package com.example.mermentv1.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mermentv1.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartModel> cartList;
    private OnQuantityChangeListener quantityChangeListener;

    public CartAdapter(Context context, List<CartModel> cartList , OnQuantityChangeListener quantityChangeListener) {
        this.context = context;
        this.cartList = cartList;
        this.quantityChangeListener = quantityChangeListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartModel cartModel = cartList.get(position);

        holder.productName.setText(cartModel.getName());
        holder.productQuantity.setText(String.valueOf(cartModel.getQuantity()));
        holder.productPrice.setText(cartModel.getPrice());  // Append " VND" to the price

        Glide.with(context)
                .load(cartModel.getImageUrl())
                .into(holder.productImage);

        holder.plusButton.setOnClickListener(v -> {
            int quantity = cartModel.getQuantity();
            cartModel.setQuantity(++quantity);
            notifyItemChanged(position);
            quantityChangeListener.onQuantityChanged(cartModel, quantity); // Pass model and new quantity
        });

        holder.minusButton.setOnClickListener(v -> {
            int quantity = cartModel.getQuantity();
            if (quantity > 1) {
                cartModel.setQuantity(--quantity);
                notifyItemChanged(position);
                quantityChangeListener.onQuantityChanged(cartModel, quantity); // Pass model and new quantity
            } else if (quantity == 1) {
                // Remove item from the cart if quantity is 1 and minus is clicked
                cartList.remove(position);
                notifyItemRemoved(position);
                quantityChangeListener.onQuantityChanged(cartModel, 0); // Notify that the item is removed
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productQuantity , productPrice;
        ImageView productImage;
        Button plusButton, minusButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
            plusButton = itemView.findViewById(R.id.btn_plus);
            minusButton = itemView.findViewById(R.id.btn_minus);
        }
    }
    public interface OnQuantityChangeListener {
        void onQuantityChanged(CartModel cartModel, int newQuantity);
        void onItemRemoved(CartModel cartModel);

    }

}
