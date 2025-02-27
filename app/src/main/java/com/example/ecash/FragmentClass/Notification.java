package com.example.ecash.FragmentClass;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecash.Adapter.NotificationAdapter;
import com.example.ecash.ModelClass.Notification_item;
import com.example.ecash.R;
import com.example.ecash.util.CustomEdgeEffect;

import java.util.ArrayList;
import java.util.List;

public class Notification extends Fragment {

    RecyclerView notification_recyclerView;
    private NotificationAdapter adapter;
    private List<Notification_item> Notification_List;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View notification_view = inflater.inflate(R.layout.fragment_notification, container, false);
        notification_recyclerView = notification_view.findViewById(R.id.notification_recyclerView);
        notification_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notification_recyclerView.setEdgeEffectFactory(new CustomEdgeEffect(getContext()));



        Notification_List = new ArrayList<>();
        Notification_List.add(new Notification_item(R.drawable.ic_banner, "grgfrgfhgthtrhth", "grgfrgfhgthtrhdvgfdgfhgfhhghtrhtrhth", "4.33 pm 25/2/2025"));
        Notification_List.add(new Notification_item(R.drawable.ic_banner, "grgfrgfhgthtrhth", "grgfrgfhgthtrhdvgfdgfhgfhhghtrhtrhth", "4.33 pm 25/2/2025"));
        Notification_List.add(new Notification_item(R.drawable.ic_banner, "grgfrgfhgthtrhth", "grgfrgfhgthtrhdvgfdgfhgfhhghtrhtrhth", "4.33 pm 25/2/2025"));
        Notification_List.add(new Notification_item(R.drawable.ic_banner, "grgfrgfhgthtrhth", "grgfrgfhgthtrhdvgfdgfhgfhhghtrhtrhth", "4.33 pm 25/2/2025"));
        Notification_List.add(new Notification_item(R.drawable.ic_banner, "grgfrgfhgthtrhth", "grgfrgfhgthtrhdvgfdgfhgfhhghtrhtrhth", "4.33 pm 25/2/2025"));
        Notification_List.add(new Notification_item(R.drawable.ic_banner, "grgfrgfhgthtrhth", "grgfrgfhgthtrhdvgfdgfhgfhhghtrhtrhth", "4.33 pm 25/2/2025"));
        Notification_List.add(new Notification_item(R.drawable.ic_banner, "grgfrgfhgthtrhth", "grgfrgfhgthtrhdvgfdgfhgfhhghtrhtrhth", "4.33 pm 25/2/2025"));
        Notification_List.add(new Notification_item(R.drawable.ic_banner, "grgfrgfhgthtrhth", "grgfrgfhgthtrhdvgfdgfhgfhhghtrhtrhth", "4.33 pm 25/2/2025"));
        Notification_List.add(new Notification_item(R.drawable.ic_banner, "grgfrgfhgthtrhth", "grgfrgfhgthtrhdvgfdgfhgfhhghtrhtrhth", "4.33 pm 25/2/2025"));

        adapter = new NotificationAdapter(Notification_List);
        notification_recyclerView.setAdapter(adapter);



        return notification_view;
    }
}