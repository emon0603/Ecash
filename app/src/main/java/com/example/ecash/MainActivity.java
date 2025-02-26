package com.example.ecash;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    RelativeLayout balance_bt;
    LinearLayout TopBarLayout;
    TextView bt_ic_tk, textView, tv_balance;
    BottomNavigationView bottomNavigationView;

    boolean Exit = false;
    boolean Exit2 = true;
    boolean isHome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balance_bt = findViewById(R.id.balance_bt);
        bt_ic_tk = findViewById(R.id.bt_ic_tk);
        textView = findViewById(R.id.text_tap);
        tv_balance = findViewById(R.id.tv_balance);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        TopBarLayout = findViewById(R.id.TopBarLayout);


        Check_Balance_Method();
        LoadBottomNavigation();

    }

    private void Check_Balance_Method(){

        balance_bt.setOnClickListener(new View.OnClickListener() {
            private boolean isClicked = false; // Track if button was clicked before

            @Override
            public void onClick(View view) {
                if (isClicked) return; // Prevent multiple clicks
                isClicked = true; // Mark as clicked

                bt_ic_tk.post(() -> {
                    View parent = (View) bt_ic_tk.getParent();
                    int parentWidth = parent.getWidth();

                    // Calculate the target X position relative to the parent
                    int targetX = parentWidth - bt_ic_tk.getWidth() - 10; // Adjust margin as needed

                    // Current X position of the view within the parent
                    int currentX = (int) bt_ic_tk.getX();

                    // Translation needed to reach the target position
                    float translationX = targetX - currentX;

                    // Move Animation (Right)
                    ObjectAnimator moveRight = ObjectAnimator.ofFloat(bt_ic_tk, "translationX", translationX);
                    moveRight.setDuration(500);
                    moveRight.setInterpolator(new AccelerateDecelerateInterpolator());

                    // Fade Out Animation for textView
                    ObjectAnimator fadeOutTextView = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f);
                    fadeOutTextView.setDuration(500);

                    // Fade In Animation for tv_balance
                    ObjectAnimator fadeInTvBalance = ObjectAnimator.ofFloat(tv_balance, "alpha", 0f, 1f);
                    fadeInTvBalance.setDuration(500);

                    AnimatorSet firstSet = new AnimatorSet();
                    firstSet.playTogether(moveRight, fadeOutTextView, fadeInTvBalance);
                    firstSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            textView.setVisibility(GONE);
                            tv_balance.setVisibility(VISIBLE);

                            // **5 seconds delay before reversing**
                            new Handler().postDelayed(() -> {
                                // Move Back to Original Position
                                ObjectAnimator moveBack = ObjectAnimator.ofFloat(bt_ic_tk, "translationX", 0);
                                moveBack.setDuration(500);
                                moveBack.setInterpolator(new AccelerateDecelerateInterpolator());

                                // Fade In Animation for textView
                                ObjectAnimator fadeInTextView = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f);
                                fadeInTextView.setDuration(500);

                                // Fade Out Animation for tv_balance
                                ObjectAnimator fadeOutTvBalance = ObjectAnimator.ofFloat(tv_balance, "alpha", 1f, 0f);
                                fadeOutTvBalance.setDuration(500);

                                AnimatorSet secondSet = new AnimatorSet();
                                secondSet.playTogether(moveBack, fadeInTextView, fadeOutTvBalance);
                                secondSet.addListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        textView.setVisibility(VISIBLE);
                                        tv_balance.setVisibility(GONE);
                                        isClicked = false; // **Enable button again**
                                    }
                                });

                                secondSet.start();

                            }, 3000); // **Wait 5 Seconds before reversing**
                        }
                    });

                    firstSet.start();
                });
            }
        });

    }

    private void LoadBottomNavigation() {

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Home()).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bottom_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Home()).commit();
                    new Handler().postDelayed(() -> TopBarLayout.setVisibility(View.VISIBLE), 100);
                    return true;
                }  else if (itemId == R.id.bottom_scan_QR) {
                    startActivity(new Intent(MainActivity.this, QRScannerActivity.class));
                    return true;
                } else if (itemId == R.id.bottom_search) {
                    TopBarLayout.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Search()).commit();
                    return true;
                }

                else if (itemId == R.id.bottom_Inbox) {
                    TopBarLayout.setVisibility(View.GONE);
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

}