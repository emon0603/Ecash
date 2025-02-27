package com.example.ecash.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecash.ModelClass.Notification_item;
import com.example.ecash.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<Notification_item> itemList;

    public NotificationAdapter(List<Notification_item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification_item model = itemList.get(position);
        holder.cover_image.setImageResource(model.getImageView());
        holder.title_tv.setText(model.getTitle());
        holder.description_tv.setText(model.getDescription());
        holder.date_tv.setText(model.getDate());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cover_image;
        TextView title_tv, description_tv, date_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cover_image = itemView.findViewById(R.id.cover_image);
            title_tv = itemView.findViewById(R.id.title_tv);
            description_tv = itemView.findViewById(R.id.description_tv);
            date_tv = itemView.findViewById(R.id.date_tv);



        }
    }
}

