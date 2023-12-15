package com.example.demonbuilderv2.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.demonbuilderv2.Users;

@Database(entities = {Users.class}, version = 1)
public abstract class DemonDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "Users.db";
    public static final String USERS_TABLE = "users_table";

    private static volatile DemonDatabase instance;
    private static final Object LOCK = new Object();

    public abstract UsersDAO UsersDAO();

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Using AsyncTask to insert users
            AsyncTask.execute(() -> {
                // Get DAO and insert users
                UsersDAO dao = instance.UsersDAO();
                dao.insert(new Users("testuser1", "testuser1", false));
                dao.insert(new Users("admin2", "admin2", true));
            });
        }
    };
    public static DemonDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            DemonDatabase.class,
                            DATABASE_NAME)
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

}

