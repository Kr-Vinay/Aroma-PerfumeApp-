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

public class CardAdapter2 extends RecyclerView.Adapter<CardAdapter2.ViewHolder> {
    private List<Card2> cardList;
    private Context context2;

    public CardAdapter2(List<Card2> cardList,Context context2) {
        this.context2=context2;
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
        Card2 currentCard = cardList.get(position);
        Glide.with(context2).load(currentCard.getImageUrl()).into(holder.imageView);
        holder.name.setText(currentCard.getName());
        holder.price.setText(currentCard.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(context2,Details.class);
                intent2.putExtra("image_details", currentCard.getDetail());
                intent2.putExtra("image_url", currentCard.getImageUrl());
                intent2.putExtra("image_name", currentCard.getName());
                intent2.putExtra("image_price", currentCard.getPrice());
                context2.startActivity(intent2);

            }
        });

    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cardimage);
            name = itemView.findViewById(R.id.cardname);
            price = itemView.findViewById(R.id.price);
        }
    }
}
