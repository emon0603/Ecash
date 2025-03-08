package com.example.ecash.FragmentClass;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecash.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.denzcoskun.imageslider.constants.ScaleTypes;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {

    RelativeLayout balance_bt;
    LinearLayout TopBarLayout;
    TextView bt_ic_tk, textView, tv_balance;
    BottomNavigationView bottomNavigationView;

    MaterialCardView seeMoreButton, closeButton;
    LinearLayout categoryLayout;
    View line, line1;

    ImageSlider imageSlider;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewhome = inflater.inflate(R.layout.fragment_home, container, false);

        balance_bt = viewhome.findViewById(R.id.balance_bt);
        bt_ic_tk = viewhome.findViewById(R.id.bt_ic_tk);
        textView = viewhome.findViewById(R.id.text_tap);
        tv_balance = viewhome.findViewById(R.id.tv_balance);
        TopBarLayout = viewhome.findViewById(R.id.TopBarLayout);

        seeMoreButton = viewhome.findViewById(R.id.seeMoreButton);
        closeButton = viewhome.findViewById(R.id.closeButton);
        categoryLayout = viewhome.findViewById(R.id.categoryLayout);
        line = viewhome.findViewById(R.id.line);
        line1 = viewhome.findViewById(R.id.line1);
        imageSlider = viewhome.findViewById(R.id.image_slider);


        // প্রথমে ক্যাটাগরি কম দেখানোর জন্য সেট করা
        categoryLayout.setVisibility(View.GONE);
        closeButton.setVisibility(View.GONE);


        // See More ক্লিক করলে Expand হবে
        seeMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryLayout.setVisibility(View.VISIBLE);
                seeMoreButton.setVisibility(View.GONE);
                closeButton.setVisibility(View.VISIBLE);
                line.setVisibility(GONE);
                line1.setVisibility(VISIBLE);
            }
        });

        // Close ক্লিক করলে আগের অবস্থায় ফিরে যাবে
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryLayout.setVisibility(View.GONE);
                seeMoreButton.setVisibility(View.VISIBLE);
                closeButton.setVisibility(View.GONE);
                line.setVisibility(VISIBLE);
                line1.setVisibility(GONE);
            }
        });


        Check_Balance_Method();




        List<SlideModel> imageList = new ArrayList<>();

        // Image URLs with Titles
        imageList.add(new SlideModel(R.drawable.banner_one,  ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.banner_one,  ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.banner_one,  ScaleTypes.CENTER_CROP));


        imageSlider.setImageList(imageList) ;


        return viewhome;
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



}