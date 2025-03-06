package com.example.ecash;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ecash.FragmentClass.Home;
import com.example.ecash.FragmentClass.Inbox;
import com.example.ecash.FragmentClass.SQScan;
import com.example.ecash.FragmentClass.Search;
import com.example.ecash.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    boolean Exit = false;
    boolean Exit2 = true;
    boolean isHome = true;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the binding object
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Set the content view using binding.root
        setContentView(binding.getRoot());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        String username = prefs.getString("username", "");




        LoadBottomNavigation();

    }


    private void LoadBottomNavigation() {

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Home()).commit();

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bottom_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Home()).commit();
                    return true;
                }  else if (itemId == R.id.bottom_scan_QR) {
                    startActivity(new Intent(MainActivity.this, QRScannerActivity.class));
                    return true;
                } else if (itemId == R.id.bottom_search) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Search()).commit();
                    return true;
                }

                else if (itemId == R.id.bottom_Inbox) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Inbox()).commit();
                    return true;
                }
                return false;
            }
        });
    }


    public void onBackPressed() {

            if (isHome && Exit2) {
                finishAffinity();
            }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up binding object to avoid memory leaks
        binding = null;
    }

}