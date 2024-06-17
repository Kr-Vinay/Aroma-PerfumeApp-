package com.example.perfumeapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<Card> cardList;
    private Context context1;

    public CardAdapter(List<Card> cardList,Context context1) {
        this.context1=context1;
        this.cardList = cardList != null ? cardList : new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Card currentCard = cardList.get(position);
        Glide.with(context1).load(currentCard.getImageUrl()).into(holder.imageView);
        holder.name.setText(currentCard.getName());
        holder.price.setText(currentCard.getPrice());
        holder.details.setText(currentCard.getDetail());
        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(context1,Details.class);
            intent.putExtra("image_details", currentCard.getDetail());
            intent.putExtra("image_url", currentCard.getImageUrl());
            intent.putExtra("image_name", currentCard.getName());
            intent.putExtra("image_price", currentCard.getPrice());
            context1.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name, price,details;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            details=itemView.findViewById(R.id.detail);
            imageView = itemView.findViewById(R.id.cardimage);
            name = itemView.findViewById(R.id.cardname);
            price = itemView.findViewById(R.id.price);

        }
    }
}
