package com.example.ecash.FragmentClass;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ecash.R;
import com.example.ecash.sendmoney.send_amount;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class MyBottomSheetFragment extends BottomSheetDialogFragment {

    TextView send_num_tv;
    public static String number = "";
    RelativeLayout next_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate and return your bottom sheet layout
        View view = inflater.inflate(R.layout.fragment_my_bottom_sheet, container, false);

        send_num_tv = view.findViewById(R.id.send_num_tv);
        next_btn = view.findViewById(R.id.next_btn);
        send_num_tv.setText(number);


        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), send_amount.class));


            }
        });





        return view;
    }

    public void closeBottomSheet() {
        dismiss();
    }
}