package com.example.ecash.Global;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ecash.R;
import com.example.ecash.sendmoney.send_amount;

import java.util.Random;

public class Enter_PIN extends AppCompatActivity {

    TextView nameTextView, numberTextView, item_text, itemText;
    ImageView item_image;
    RelativeLayout itemContainer, next_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enter_pin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        nameTextView = findViewById(R.id.name_text_view);
        numberTextView = findViewById(R.id.numberTextView);
        itemText = findViewById(R.id.itemText);
        item_image = findViewById(R.id.item_image);
        itemContainer = findViewById(R.id.item_container);
        next_btn = findViewById(R.id.next_btn);

        String name = getIntent().getStringExtra("name");
        String number = getIntent().getStringExtra("number");



        String text = name.toString();  // Full name nichi

        nameTextView.setText(name);
        numberTextView.setText(number);

        // Prothom character niye `itemText` e set kora
        if (!text.isEmpty()) {
            char firstChar = text.trim().charAt(0);  // Trim kore prothom character nichi

            if (firstChar == '+'){
                item_image.setImageResource(R.drawable.ic_person);
                item_image.setVisibility(VISIBLE);
                itemText.setVisibility(GONE);
            } else {
                itemText.setText(String.valueOf(firstChar).toUpperCase());  // Capital letter e set korchi
                item_image.setVisibility(GONE);
                itemText.setVisibility(VISIBLE);
            }


        }

        // Random color generate kora
        Random random = new Random();
        int randomColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));

        // Round background er color change kora
        itemContainer.getBackground().setTint(randomColor);


        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(send_amount.this, Enter_PIN.class));
            }
        });


    }
}