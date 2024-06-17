package com.example.perfumeapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InvoiceActivity extends AppCompatActivity {

    private TextView invoicePaymentID, invoiceAmount, invoiceItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        invoicePaymentID = findViewById(R.id.invoicePaymentID);
        invoiceAmount = findViewById(R.id.invoiceAmount);
        invoiceItems = findViewById(R.id.invoiceItems);

        // Retrieve payment details from intent
        String paymentID = getIntent().getStringExtra("paymentID");
        double totalAmount = getIntent().getDoubleExtra("totalAmount", 0);
        ArrayList<CartItem> cartItems = (ArrayList<CartItem>) getIntent().getSerializableExtra("cartItems");

        // Display payment details
        invoicePaymentID.setText("Payment ID: " + paymentID);
        invoiceAmount.setText("Total Amount: $" + String.format("%.2f", totalAmount));

        StringBuilder itemsStringBuilder = new StringBuilder();
        for (CartItem item : cartItems) {
            itemsStringBuilder.append(item.getName())
                    .append(" - $")
                    .append(item.getPrice())
                    .append(" x ")
                    .append(item.getQuantity())
                    .append("\n");
        }
        invoiceItems.setText(itemsStringBuilder.toString());
    }
}
