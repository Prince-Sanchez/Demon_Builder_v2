package com.example.demonbuilderv2.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.demonbuilderv2.Users;

@Database(entities = {Users.class}, version = 1)
public abstract class DemonDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "Users.db";
    public static final String USERS_TABLE = "users_table";

    private static volatile DemonDatabase instance;
    private static final Object LOCK = new Object();

    public abstract UsersDAO UsersDAO();
    public static DemonDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            DemonDatabase.class,
                            DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }

}

