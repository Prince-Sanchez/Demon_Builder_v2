package com.example.demonbuilderv2.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.demonbuilderv2.Users;

@Dao
public interface UsersDAO {

    @Insert
    void insert(Users... users);

    @Update
    void update(Users... users);

    @Delete
    void delete(Users users);

    // Method for user authentication
    @Query("SELECT * FROM users_table WHERE mUsername = :username AND mPassword = :password")
    Users getUserByUsernameAndPassword(String username, String password);
}
