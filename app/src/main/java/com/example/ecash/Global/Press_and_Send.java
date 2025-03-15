package com.example.ecash.Global;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ecash.MainActivity;
import com.example.ecash.R;

public class Press_and_Send extends AppCompatActivity {

    private boolean isLongPress = false;
    private Handler handler = new Handler();

    LinearLayout tap_and_hold_btn;

    private ObjectAnimator animator;
    private ImageView progressBar;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_press_and_send);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tap_and_hold_btn = findViewById(R.id.tap_and_hold_btn);
        progressBar = findViewById(R.id.progressBar); // Animation Bar


        tap_and_hold_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isLongPress = false;
                        handler.postDelayed(longPressRunnable, 1000); // 1 second long press
                        break;

                    case MotionEvent.ACTION_UP:
                        handler.removeCallbacks(longPressRunnable);
                        if (!isLongPress) {
                            Toast.makeText(Press_and_Send.this, "Short Click", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return true;
            }
        });



    }


    private Runnable longPressRunnable = new Runnable() {
        @Override
        public void run() {
            isLongPress = true;
            startAnimation(); // Animation Start korbe
            Toast.makeText(Press_and_Send.this, "Long Press Detected!", Toast.LENGTH_SHORT).show();
        }
    };


    private void startAnimation() {
        animator = ObjectAnimator.ofFloat(progressBar, "translationX", 0f, 600f); // Left theke Right e
        animator.setDuration(2000); // 2 second e complete hobe
        animator.start();
    }

    private void stopAnimation() {
        if (animator != null) {
            animator.cancel();
            progressBar.setTranslationX(0f); // Bar Reset kore dibe
        }
    }


}