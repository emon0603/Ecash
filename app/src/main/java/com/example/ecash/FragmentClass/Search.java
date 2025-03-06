package com.example.ecash.FragmentClass;

import static android.content.Context.INPUT_METHOD_SERVICE;

import static com.google.android.material.internal.ViewUtils.showKeyboard;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.ecash.R;
import com.example.ecash.databinding.FragmentSearchBinding;

import java.lang.reflect.Method;

public class Search extends Fragment {

    private FragmentSearchBinding binding;
    private String[] dynamicHints = {"offers", "contracts", "services", "orgra"};
    private int hintIndex = 0;
    CharSequence hint;
    String edtext;
    String[] words ;
    String change_text = "";  // Initialize the third word

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentSearchBinding.inflate(inflater,container,false);

        hint = binding.searchEd.getHint();
        Log.d("Hint", hint.toString());
        edtext = hint.toString();
        words = edtext.split(" "); // Split the string by spaces

        Custome_keyboard();
        changeHintTextPeriodically();


        return binding.getRoot();




    }

    private void Custome_keyboard(){

        binding.searchEd.setCursorVisible(true); // কার্সর সবসময় দেখাবে
        binding.searchEd.setFocusable(true); // EditText-এ ফোকাস রাখবে
        binding.searchEd.requestFocus(); // EditText-এ ফোকাস রাখবে
        binding.searchEd.setSelection(binding.searchEd.getText().length()); // কার্সর শেষে নিন

        new Handler().postDelayed(() -> showKeyboard(binding.searchEd), 300);

        
        // 2. কার্সর টেক্সটের শেষে সেট করুন (প্রথমবারের জন্য)
        binding.searchEd.post(new Runnable() {
            @Override
            public void run() {
                binding.searchEd.setSelection(binding.searchEd.getText().length());
            }
        });

        // 4. টেক্সট চেঞ্জ হলে কার্সর শেষে রাখুন (ঐচ্ছিক)
        binding.searchEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                binding.searchEd.setSelection(s.length());
            }
        });


    }

    private void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }


    // Method to change the hint text periodically with animation
    private void changeHintTextPeriodically() {



        binding.searchEd.setHint("Search for " + dynamicHints[hintIndex]);

        final Handler handler = new Handler();
        final Runnable updateHintRunnable = new Runnable() {
            @Override
            public void run() {
                // Create animation for moving the current dynamic hint up
                ObjectAnimator animatorUp = ObjectAnimator.ofFloat(binding.searchEd, "translationY", 0f, -100f);
                animatorUp.setDuration(300);
                animatorUp.setInterpolator(new AccelerateDecelerateInterpolator());

                // Create animation for moving the new dynamic hint from below
                ObjectAnimator animatorDown = ObjectAnimator.ofFloat(binding.searchEd, "translationY", 100f, 0f);
                animatorDown.setDuration(300);
                animatorDown.setInterpolator(new AccelerateDecelerateInterpolator());

                // Start the animations
                animatorUp.start();

                // Wait for the "up" animation to finish before setting the new dynamic hint
                animatorUp.addListener(new android.animation.Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(android.animation.Animator animation) {
                        // Do nothing here
                    }

                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        // Update the dynamic part of the hint after the "up" animation ends


                        hintIndex = (hintIndex + 1) % dynamicHints.length;  // Loop to the next dynamic hint
                        edtext = dynamicHints[hintIndex];
                        if (words.length >= 3) {
                            change_text = dynamicHints[hintIndex];  // Access the 3rd word (index 2)
                        }

                        binding.searchEd.setHint("Search for " + change_text);  // Keep "Search for" static



                        Log.d("Third Word", change_text);

                        // Start the "down" animation for the new dynamic hint
                        animatorDown.start();
                    }

                    @Override
                    public void onAnimationCancel(android.animation.Animator animation) {
                        // Do nothing here
                    }

                    @Override
                    public void onAnimationRepeat(android.animation.Animator animation) {
                        // Do nothing here
                    }
                });

                // Repeat the process after 1 second (1000 milliseconds)
                handler.postDelayed(this, 1000);
            }
        };

        // Start the periodic dynamic hint change with animation
        handler.post(updateHintRunnable);
    }

}