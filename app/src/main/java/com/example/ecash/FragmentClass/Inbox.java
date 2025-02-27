package com.example.ecash.FragmentClass;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecash.Adapter.ViewPagerAdapter;
import com.example.ecash.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class Inbox extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inboxview = inflater.inflate(R.layout.fragment_inbox, container, false);
        tabLayout = inboxview.findViewById(R.id.tabLayout);
        viewPager = inboxview.findViewById(R.id.viewPager);




        TabLayoutLoad();


        return inboxview;
    }

    private void TabLayoutLoad() {


        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.inbox_frag, new Transaction()).commit();

        // Initialize Adapter
        adapter = new ViewPagerAdapter(getActivity());
        viewPager.setAdapter(adapter);

        // Attach TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Transaction");
                        break;
                    case 1:
                        tab.setText("Notification");
                        break;
                }
            }
        }).attach();

    }



}