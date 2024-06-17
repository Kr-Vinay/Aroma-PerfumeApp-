package com.example.perfumeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cart extends AppCompatActivity implements CartAdapter.OnCartItemChangeListener, PaymentResultListener {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList;
    private TextView subtotal, shipping, total, emptyCartMessage;
    private ImageView backbtn;
    private Button checkoutbtn;
    private double totalAmountWithShipping; // Declare class-level variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize UI elements
        backbtn = findViewById(R.id.cartbackbtn);
        subtotal = findViewById(R.id.subtotal);
        total = findViewById(R.id.total);
        shipping = findViewById(R.id.shipping);
        emptyCartMessage = findViewById(R.id.cartemptymessage);
        checkoutbtn = findViewById(R.id.checkoutbtn);

        recyclerView = findViewById(R.id.cartrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItemList = new ArrayList<>();

        // Load cart items from SharedPreferences
        loadCartItems();

        // Retrieve item details from Intent
        String itemName = getIntent().getStringExtra("image_name");
        String itemPrice = getIntent().getStringExtra("imagePrice");
        String itemImageUrl = getIntent().getStringExtra("imageUrl");

        if (itemName != null && itemPrice != null && itemImageUrl != null) {
            cartItemList.add(new CartItem(itemName, itemPrice, itemImageUrl, 1));
            // Save cart items to SharedPreferences
            saveCartItems();
        }

        cartAdapter = new CartAdapter(cartItemList, this);
        recyclerView.setAdapter(cartAdapter);

        calculateTotal();

        // Handle back button click
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Initialize Razorpay
        Checkout.preload(getApplicationContext());

        // Handle checkout button click
        checkoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    @Override
    public void onCartItemChange() {
        calculateTotal();
        saveCartItems();
    }

    public void onRemoveItemClick(int position) {
        removeItem(position);
    }

    private void removeItem(int position) {
        cartItemList.remove(position);
        cartAdapter.notifyItemRemoved(position);
        calculateTotal();
        saveCartItems();
    }

    private void calculateTotal() {
        double subtotalAmount = 0;
        for (CartItem item : cartItemList) {
            subtotalAmount += Double.parseDouble(item.getPrice()) * item.getQuantity();
        }
        subtotal.setText(String.format("$%.2f", subtotalAmount));

        int shippingCost = cartItemList.isEmpty() ? 0 : 99;
        shipping.setText(String.format("$%d", shippingCost));

        totalAmountWithShipping = subtotalAmount + shippingCost;
        total.setText(String.format("$%.2f", totalAmountWithShipping));

        if (cartItemList.isEmpty()) {
            emptyCartMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyCartMessage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_nNKjAJzCWUwDVi"); // Replace with your key ID

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Aroma");
            options.put("description", "Payment for Cart Items");
            options.put("currency", "USD");
            options.put("amount", totalAmountWithShipping * 100); // Razorpay requires the amount in paise

            checkout.open(this, options);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error in starting Razorpay Checkout", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, InvoiceActivity.class);
        intent.putExtra("paymentID", razorpayPaymentID);
        intent.putExtra("totalAmount", totalAmountWithShipping); // Pass the total amount with shipping
        intent.putExtra("cartItems", new ArrayList<>(cartItemList)); // Pass the cart items
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int code, String response) {
        Toast.makeText(this, "Payment Failed: " + response, Toast.LENGTH_SHORT).show();
    }

    private void saveCartItems() {
        SharedPreferences sharedPreferences = getSharedPreferences("CartPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> cartItemsSet = new HashSet<>();
        for (CartItem item : cartItemList) {
            cartItemsSet.add(item.getName() + "," + item.getPrice() + "," + item.getImageUrl() + "," + item.getQuantity());
        }
        editor.putStringSet("cartItems", cartItemsSet);
        editor.apply();
    }

    private void loadCartItems() {
        SharedPreferences sharedPreferences = getSharedPreferences("CartPrefs", Context.MODE_PRIVATE);
        Set<String> cartItemsSet = sharedPreferences.getStringSet("cartItems", new HashSet<>());

        for (String itemString : cartItemsSet) {
            String[] itemDetails = itemString.split(",");
            String name = itemDetails[0];
            String price = itemDetails[1];
            String imageUrl = itemDetails[2];
            int quantity = Integer.parseInt(itemDetails[3]);
            cartItemList.add(new CartItem(name, price, imageUrl, quantity));
        }
    }
}
