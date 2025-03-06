package com.example.ecash;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ecash.databinding.ActivityLoginBinding;

import java.lang.reflect.Method;

public class Login extends AppCompatActivity {


    private StringBuilder pinInput = new StringBuilder();
    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the binding object
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        // Set the content view using binding.root
        setContentView(binding.getRoot());


        Custome_keyboard();





    }

    private void Custome_keyboard(){

        binding.pinEditText.setCursorVisible(true); // কার্সর সবসময় দেখাবে
        binding.pinEditText.requestFocus(); // EditText-এ ফোকাস রাখবে
        binding.pinEditText.setSelection(binding.pinEditText.getText().length()); // কার্সর শেষে নিন

        // For API 21 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.pinEditText.setShowSoftInputOnFocus(false);
        } else {
            // For older APIs, use reflection
            try {
                Method method = EditText.class.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(binding.pinEditText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 2. কার্সর টেক্সটের শেষে সেট করুন (প্রথমবারের জন্য)
        binding.pinEditText.post(new Runnable() {
            @Override
            public void run() {
                binding.pinEditText.setSelection(binding.pinEditText.getText().length());
            }
        });

        // 4. টেক্সট চেঞ্জ হলে কার্সর শেষে রাখুন (ঐচ্ছিক)
        binding.pinEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                binding.pinEditText.setSelection(s.length());
            }
        });





        // Number Buttons
        setNumberButton(R.id.btn0, "0");
        setNumberButton(R.id.btn1, "1");
        setNumberButton(R.id.btn2, "2");
        setNumberButton(R.id.btn3, "3");
        setNumberButton(R.id.btn4, "4");
        setNumberButton(R.id.btn5, "5");
        setNumberButton(R.id.btn6, "6");
        setNumberButton(R.id.btn7, "7");
        setNumberButton(R.id.btn8, "8");
        setNumberButton(R.id.btn9, "9");

        // Backspace Button
        Button btnBackspace = findViewById(R.id.btnBackspace);
        btnBackspace.setOnClickListener(v -> {
            if (pinInput.length() > 0) {
                pinInput.deleteCharAt(pinInput.length() - 1);
                updatePinDisplay();
            }
        });
    }

    private void setNumberButton(int buttonId, String number) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            if (pinInput.length() < 5) {
                pinInput.append(number);
                updatePinDisplay();
            }
        });
    }

    private void updatePinDisplay() {
        String maskedPin = "";
        for (int i = 0; i < pinInput.length(); i++) {
            maskedPin += "●";
        }
        binding.pinEditText.setText(maskedPin);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up binding object to avoid memory leaks
        binding = null;
    }


}