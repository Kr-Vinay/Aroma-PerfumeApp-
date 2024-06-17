package com.example.perfumeapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Liked extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked);

        // Initialize UI elements
        ListView likedItemsListView = findViewById(R.id.LikedItemListView);

        // Get liked items from SharedPreferences
        Set<String> likedItemsSet = getLikedItems();
        List<CartItem> likedItemsList = new ArrayList<>();
        for (String itemString : likedItemsSet) {
            String[] itemDetails = itemString.split(",");
            // Check if the itemDetails array contains all three parts
            if (itemDetails.length >= 3) {
                String name = itemDetails[0];
                String price = itemDetails[1];
                String imageUrl = itemDetails[2];
                likedItemsList.add(new CartItem(name, price, imageUrl, 1)); // Assuming you have a CartItem class
            }
        }

        // Create adapter and set it to the ListView
        LikedItemsAdapter adapter = new LikedItemsAdapter(this, likedItemsList);
        likedItemsListView.setAdapter(adapter);
    }

    private Set<String> getLikedItems() {
        SharedPreferences sharedPreferences = getSharedPreferences("LikedItems", Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet("likedItems", new HashSet<>());
    }
}
