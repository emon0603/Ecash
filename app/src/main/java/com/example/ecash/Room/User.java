package com.example.ecash.Room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {


    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String number;


    public User(@NonNull String name, @NonNull String number) {
        this.name = name != null ? name : "Unknown"; // Null check
        this.number = number != null ? number : "Unknown"; // Null check
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
