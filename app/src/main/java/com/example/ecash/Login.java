package com.example.ecash;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    private EditText pinEditText;
    private StringBuilder pinInput = new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        pinEditText = findViewById(R.id.pinEditText);



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
            if (pinInput.length() < 4) {
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
        pinEditText.setText(maskedPin);
    }



}