package com.example.perfumeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class Details extends AppCompatActivity {
    ImageView backButton;
    Button PutInbagbtn;
    ImageView shareButton;
    ImageView likeButton;
    String itemName, itemPrice, imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        backButton = findViewById(R.id.backbtn);
        PutInbagbtn = findViewById(R.id.putinbag);
        shareButton = findViewById(R.id.sharebutton);
        likeButton = findViewById(R.id.likeButton);

        backButton.setOnClickListener(v -> {
            finish();
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDetails();
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToLikedItems();
            }
        });

        // Get data from Intent
        imageUrl = getIntent().getStringExtra("image_url");
        String detail = getIntent().getStringExtra("image_details");
        itemName = getIntent().getStringExtra("image_name");
        itemPrice = getIntent().getStringExtra("image_price");

        // Set data to views
        TextView nameTextView = findViewById(R.id.detailname);
        TextView priceTextView = findViewById(R.id.detailprice);
        ImageView imageView = findViewById(R.id.detailimage);
        TextView details = findViewById(R.id.image_detail);

        nameTextView.setText(itemName);
        priceTextView.setText(String.valueOf(itemPrice));
        details.setText(String.valueOf(detail));
        Glide.with(this).load(imageUrl).into(imageView);

        PutInbagbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
    }

    private void addToCart() {
        Intent i = new Intent(Details.this, Cart.class);
        i.putExtra("image_name", itemName);
        i.putExtra("imagePrice", itemPrice);
        i.putExtra("imageUrl", imageUrl);
        startActivity(i);
    }

    private void shareDetails() {
        String itemDescription = "Check out this amazing item: " + itemName + " - Price: $" + itemPrice;

        // Load the image from URL
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(imageUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    if (bitmap != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                shareImageAndText(bitmap, itemDescription);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void shareImageAndText(Bitmap bitmap, String itemDescription) {
        // Convert bitmap to byte array
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        Uri imageUri = Uri.parse(path);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, itemDescription);
        sendIntent.putExtra(Intent.EXTRA_STREAM, imageUri); // Attach image URI
        sendIntent.setType("image/jpeg"); // Set MIME type as JPEG

        // Start chooser to let the user choose where to share
        startActivity(Intent.createChooser(sendIntent, "Share Item"));
    }

    private void addToLikedItems() {
        // Concatenate all details into a single string separated by commas
        String likedItem = itemName + "," + itemPrice + "," + imageUrl;
        Set<String> likedItems = getLikedItems();
        likedItems.add(likedItem);
        saveLikedItems(likedItems);
        Toast.makeText(this, "Added to Liked Items", Toast.LENGTH_SHORT).show();
    }

    private Set<String> getLikedItems() {
        SharedPreferences sharedPreferences = getSharedPreferences("LikedItems", Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet("likedItems", new HashSet<>());
    }

    private void saveLikedItems(Set<String> likedItems) {
        SharedPreferences sharedPreferences = getSharedPreferences("LikedItems", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("likedItems", likedItems);
        editor.apply();
    }
}
