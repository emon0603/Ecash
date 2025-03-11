package com.example.ecash.Adapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecash.ModelClass.Contact_item;
import com.example.ecash.R;
import com.example.ecash.Room.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {



    private List<User> userList;
    private Context context;

    public ContactAdapter(Context context) {
        this.context = context;
        userList = new ArrayList<>();

    }

    public void filterList(List<User> filteredList) {
        userList = filteredList;
        notifyDataSetChanged();
    }



    public void adduser(User items) {
        userList.add(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = userList.get(position);
        String text = user.getName().toString();  // Full name nichi

        holder.name_tv.setText(user.getName());
        holder.number_tv.setText(user.getNumber());

        // Prothom character niye `itemText` e set kora
        if (!text.isEmpty()) {
            char firstChar = text.trim().charAt(0);  // Trim kore prothom character nichi

            if (firstChar == '+'){
                holder.item_image.setImageResource(R.drawable.ic_person);
                holder.item_image.setVisibility(VISIBLE);
                holder.itemText.setVisibility(GONE);
            } else {
                holder.itemText.setText(String.valueOf(firstChar).toUpperCase());  // Capital letter e set korchi
                holder.item_image.setVisibility(GONE);
                holder.itemText.setVisibility(VISIBLE);
            }


        }

        // Random color generate kora
        Random random = new Random();
        int randomColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));

        // Round background er color change kora
        holder.itemContainer.getBackground().setTint(randomColor);



    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemText, name_tv, number_tv;
        View itemContainer;
        ImageView item_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.item_text);
            item_image = itemView.findViewById(R.id.item_image);
            itemContainer = itemView.findViewById(R.id.item_container);
            name_tv = itemView.findViewById(R.id.name_tv);
            number_tv = itemView.findViewById(R.id.number_tv);
        }
    }
}
