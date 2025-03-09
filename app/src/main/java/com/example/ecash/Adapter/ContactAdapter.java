package com.example.ecash.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecash.R;

import java.util.List;
import java.util.Random;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private final List<String> items;

    public ContactAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = holder.name_tv.getText().toString();  // Full name nichi

        // Prothom character niye `itemText` e set kora
        if (!text.isEmpty()) {
            char firstChar = text.trim().charAt(0);  // Trim kore prothom character nichi
            holder.itemText.setText(String.valueOf(firstChar).toUpperCase());  // Capital letter e set korchi
        }

        // Random color generate kora
        Random random = new Random();
        int randomColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));

        // Round background er color change kora
        holder.itemContainer.getBackground().setTint(randomColor);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemText, name_tv;
        View itemContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.item_text);
            itemContainer = itemView.findViewById(R.id.item_container);
            name_tv = itemView.findViewById(R.id.name_tv);
        }
    }
}
