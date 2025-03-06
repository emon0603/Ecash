package com.example.ecash;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecash.databinding.ActivityQrscannerBinding;
import com.example.ecash.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 3 সেকেন্ড পর MainActivity তে যাওয়ার জন্য Handler ব্যবহার করা হয়েছে
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish(); // SplashActivity বন্ধ করে দেয়া হবে
            }
        }, 500); // 0.5 সেকেন্ড
    }
}
