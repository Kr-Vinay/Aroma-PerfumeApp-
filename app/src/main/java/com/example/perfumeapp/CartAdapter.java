package com.example.perfumeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private OnCartItemChangeListener listener;

    public CartAdapter(List<CartItem> cartItems, OnCartItemChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.nameTextView.setText(item.getName());
        holder.priceTextView.setText("$" + item.getPrice());
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));
        Glide.with(holder.itemView.getContext()).load(item.getImageUrl()).into(holder.imageView);

        updateTotalPrice(holder, item);

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setQuantity(item.getQuantity() + 1);
                holder.quantityTextView.setText(String.valueOf(item.getQuantity()));
                updateTotalPrice(holder, item);
                listener.onCartItemChange();
            }
        });

        holder.subtractButton.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                holder.quantityTextView.setText(String.valueOf(item.getQuantity()));
                updateTotalPrice(holder, item);
                listener.onCartItemChange();
            }
        });

        holder.removeButton.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            listener.onCartItemChange();
        });
    }

    private void updateTotalPrice(CartViewHolder holder, CartItem item) {
        double totalPrice = Double.parseDouble(item.getPrice()) * item.getQuantity();
        holder.totalPriceTextView.setText("$" + String.format("%.2f", totalPrice));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public interface OnCartItemChangeListener {
        void onCartItemChange();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView priceTextView;
        ImageView imageView;
        ImageView addButton, subtractButton;
        TextView quantityTextView;
        TextView totalPriceTextView;
        Button removeButton;

        CartViewHolder(View itemView) {
            super(itemView);
            removeButton = itemView.findViewById(R.id.cartremoveButtun);
            nameTextView = itemView.findViewById(R.id.cartname);
            priceTextView = itemView.findViewById(R.id.cartprice);
            imageView = itemView.findViewById(R.id.cartcardimage);
            addButton = itemView.findViewById(R.id.addbtn);
            subtractButton = itemView.findViewById(R.id.subtractbtn);
            quantityTextView = itemView.findViewById(R.id.cartvalue);
            totalPriceTextView = itemView.findViewById(R.id.totalprice);
        }
    }
}
