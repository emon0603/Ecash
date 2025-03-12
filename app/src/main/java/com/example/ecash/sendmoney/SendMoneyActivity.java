package com.example.ecash.sendmoney;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecash.Adapter.ContactAdapter;
import com.example.ecash.FragmentClass.MyBottomSheetFragment;
import com.example.ecash.ModelClass.Contact_item;
import com.example.ecash.R;
import com.example.ecash.Room.User;
import com.example.ecash.Room.UserDao;
import com.example.ecash.Room.UserDatabase;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendMoneyActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayout noResultLayout;
    EditText search_ed;
    TextView sendMoneyText, nameTextView, numberTextView;
    private List<Contact_item> contact_List;;

    ContactAdapter adapter;
    private UserDatabase userDatabase;
    private UserDao userDao;
    Button insert_button, continueButton;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();


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

        search_ed = findViewById(R.id.search_ed);
        noResultLayout = findViewById(R.id.no_result_layout);
        sendMoneyText = findViewById(R.id.sendMoneyText);
        continueButton = findViewById(R.id.continueButton);


        userDatabase = UserDatabase.getDatabase(this);
        userDao = userDatabase.userDao();


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample data


       adapter = new ContactAdapter(this);
       recyclerView.setAdapter(adapter);




        search_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of MyBottomSheetFragment
                MyBottomSheetFragment bottomSheetFragment = new MyBottomSheetFragment();
                // Show the bottom sheet fragment using the FragmentManager
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        FetchData();

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
                                User user = new User(name, phoneNumber);
                                addUserIfNotExists(userDao, user);

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

    private void FetchData(){
        executorService.execute(() -> {
        List<User> userslist = userDao.getAllUsers();

        for (int x = 0; x < userslist.size(); x++) {
           User users = userslist.get(x);
            adapter.adduser(users);
        }

        });





    }

    public void addUserIfNotExists(UserDao userDao, User user) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            int count = userDao.checkUserExists(user.getName(), user.getNumber());
            if (count == 0) {
                userDao.insertUsers((List<User>) user);
                System.out.println("User inserted successfully.");
            } else {
                System.out.println("User already exists.");
            }
        });
        executorService.shutdown();
    }


    private void filter(String text) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<User> allUsers = userDao.getAllUsers();
            List<User> filteredList = new ArrayList<>();

            for (User user : allUsers) {
                if (user.getName().toLowerCase().contains(text.toLowerCase()) ||
                        user.getNumber().contains(text)) {
                    filteredList.add(user);
                }
            }


            // UI থ্রেডে কাজ করবো
            new Handler(Looper.getMainLooper()).post(() -> {
                if (filteredList.isEmpty()) {
                    if (isValidMobileNumber(text)) {  // মোবাইল নম্বর ভ্যালিড কিনা চেক করবো
                    recyclerView.setVisibility(View.GONE);
                    noResultLayout.setVisibility(View.VISIBLE);
                    continueButton.setVisibility(View.VISIBLE);
                    sendMoneyText.setText("Send Money to " + text);
                    MyBottomSheetFragment.number = text;
                    } else {
                        noResultLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        continueButton.setVisibility(View.GONE);
                        sendMoneyText.setText("No maching contact");
                    }
                } else {
                    noResultLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    noResultLayout.setVisibility(View.GONE);
                    adapter.filterList(filteredList);
                }
            });
        });
        executor.shutdown();
    }

    private boolean isValidMobileNumber(String number) {
        return number.matches("^(01[3-9])\\d{8}$");  // বাংলাদেশি মোবাইল নম্বরের জন্য
    }


    @Override
    protected void onResume() {
        super.onResume();

        MyBottomSheetFragment myBottomSheetFragment = (MyBottomSheetFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (myBottomSheetFragment != null) {
            myBottomSheetFragment.closeBottomSheet();
        } else {
            Log.e("Fragment Error", "HomeFragment is not found.");
        }


    }
}