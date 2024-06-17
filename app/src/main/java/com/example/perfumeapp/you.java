package com.example.perfumeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class you extends AppCompatActivity {
    public TextView orders,cart,liked,Explore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_you);
            orders=findViewById(R.id.yourorder);
            cart=findViewById(R.id.cart);
            liked=findViewById(R.id.liked);
            Explore=findViewById(R.id.expolore);

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(you.this,Cart.class);
                startActivity(i);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(you.this,Cart.class);
                startActivity(i);
            }
        });
        liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(you.this,Liked.class);
                startActivity(i);
            }
        });

    }
}