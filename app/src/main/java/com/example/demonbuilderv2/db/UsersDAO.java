package com.example.demonbuilderv2.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
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
}
