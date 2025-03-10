package com.example.ecash;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecash.Adapter.ContactAdapter;
import com.example.ecash.Adapter.NotificationAdapter;
import com.example.ecash.ModelClass.Contact_item;
import com.example.ecash.ModelClass.Notification_item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendMoneyActivity extends AppCompatActivity {

    private List<Contact_item> contact_List;;

    ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_money);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample data
        contact_List = new ArrayList<>();
        contact_List.add(new Contact_item("Emon", "01783228430"));
        contact_List.add(new Contact_item("Afsffddfd", "8743228430"));
        contact_List.add(new Contact_item("waefg", "65143228430"));
        contact_List.add(new Contact_item("aafwe", "426283228430"));
        contact_List.add(new Contact_item("fdhbEmon", "313228430"));
        contact_List.add(new Contact_item("nhhgnh", "ghnhgn1401"));

       adapter = new ContactAdapter(contact_List);
        recyclerView.setAdapter(adapter);

        //fetchContacts();

    }



    @SuppressLint("Range")
    private ArrayList<String> fetchContacts() {
        ArrayList<String> info = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_CONTACTS") == PackageManager.PERMISSION_GRANTED) {
            ContentResolver cr = getContentResolver();
            Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

            if ((cursor != null ? cursor.getCount() : 0) > 0) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex((ContactsContract.Contacts.DISPLAY_NAME)));

                    if (cursor.getInt(cursor.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null
                        );

                        if (pCur != null) {
                            while (pCur.moveToNext()) {
                                String phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                info.add("\n ID=>" + id + "\n Name=>" + name + "\n Phone Number=>" + phoneNumber + "\n");
                                Log.d("Contacts", "ID: " + id + ", Name: " + name + ", Phone Number: " + phoneNumber);

                            }
                            pCur.close();
                        }
                    }
                }
                cursor.close();
            }
        }
        return info;
    }
}