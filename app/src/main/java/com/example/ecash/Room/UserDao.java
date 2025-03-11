package com.example.ecash.Room;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import androidx.room.Dao;

@Dao // Add this annotation
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUsers(List<User> users);

    @Query("SELECT COUNT(*) FROM User WHERE name = :name AND number = :number")
    int checkUserExists(String name, String number);

    @Query("SELECT * FROM User ORDER BY name ASC")
    List<User> getAllUsers();

    @Query("DELETE FROM User")
    void deleteAllUsers();
}

