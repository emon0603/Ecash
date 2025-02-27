package com.example.ecash.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ecash.FragmentClass.Notification;
import com.example.ecash.FragmentClass.Transaction;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Transaction();
            case 1:
                return new Notification();
            default:
                return new Transaction();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Total tabs
    }
}

