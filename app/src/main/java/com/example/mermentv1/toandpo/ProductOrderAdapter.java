package com.example.mermentv1.toandpo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mermentv1.R;
import com.example.mermentv1.model.ProductOrder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ProductOrderAdapter extends RecyclerView.Adapter<ProductOrderAdapter.ProductOrderViewHolder> {

    private final List<ProductOrder> productOrders;

    public ProductOrderAdapter(List<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

    @NonNull
    @Override
    public ProductOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ProductOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrderViewHolder holder, int position) {
        ProductOrder order = productOrders.get(position);

        // Set description
        holder.tvDescription.setText(order.getDescription());

        // Format and set order date
        String formattedDate = formatOrderDate(order.getOrderDate());
        holder.tvOrderDate.setText(formattedDate);

        // Format and set total price (assuming getTotalPrice() returns a Double)
        double totalPrice = order.getTotalPrice(); // Get total price as a double
        holder.tvTotalPrice.setText(String.format("Total Price: %.2f", totalPrice)); // Format to two decimal places

        // Set order status
        String status = order.getStatusOrder() == null ? "Pending" : order.getStatusOrder();
        holder.tvStatus.setText(String.format("Status: %s", status));
    }


    @Override
    public int getItemCount() {
        return productOrders.size();
    }

    // ViewHolder for the product order
    public static class ProductOrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription, tvOrderDate, tvTotalPrice, tvStatus;

        public ProductOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            tvStatus = itemView.findViewById(R.id.tvStatusOrder);
        }
    }

    /**
     * Formats the order date from ISO string to a readable format.
     */
    private String formatOrderDate(String orderDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
            return outputFormat.format(inputFormat.parse(orderDate));
        } catch (Exception e) {
            e.printStackTrace();
            return orderDate; // Return the original date if parsing fails
        }
    }
}
