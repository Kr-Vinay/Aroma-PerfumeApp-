package com.example.perfumeapp.splashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perfumeapp.MainActivity;
import com.example.perfumeapp.R;

public class splashActivity extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_LENGTH =2000; // Duration in milliseconds (2 seconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(splashActivity.this, MainActivity.class);
                splashActivity.this.startActivity(mainIntent);
                splashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}