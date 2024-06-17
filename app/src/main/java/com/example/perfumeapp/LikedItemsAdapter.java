package com.example.perfumeapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.List;

public class LikedItemsAdapter extends ArrayAdapter<CartItem> {

    public LikedItemsAdapter(Context context, List<CartItem> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.likeditemlayout, parent, false);
        }

        CartItem currentItem = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.cartname);
        TextView priceTextView = listItemView.findViewById(R.id.cartprice);
        ImageView imageView = listItemView.findViewById(R.id.imageView);

        if (currentItem != null) {
            nameTextView.setText(currentItem.getName());
            priceTextView.setText(currentItem.getPrice());

            // Load image using Glide library
            Glide.with(getContext())
                    .load(currentItem.getImageUrl())

                    .into(imageView);
        }

        return listItemView;
    }
}

