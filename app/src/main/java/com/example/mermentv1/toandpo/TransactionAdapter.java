package com.example.mermentv1.toandpo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mermentv1.R;
import com.example.mermentv1.model.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private final List<Transaction> transactionList;

    public TransactionAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);

        holder.tvTransactionId.setText("ID: " + transaction.getId());
        holder.tvTransactionDescription.setText("Description: " + transaction.getDescription());
        holder.tvTransactionAmount.setText("Amount: $" + transaction.getAmount());
        holder.tvTransactionStatus.setText("Status: " + transaction.getStatus());
    }



    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView tvTransactionId, tvTransactionDescription, tvTransactionAmount, tvTransactionStatus;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTransactionId = itemView.findViewById(R.id.tvTransactionId);
            tvTransactionDescription = itemView.findViewById(R.id.tvTransactionDescription);
            tvTransactionAmount = itemView.findViewById(R.id.tvTransactionAmount);
            tvTransactionStatus = itemView.findViewById(R.id.tvTransactionStatus);
        }
    }
}
