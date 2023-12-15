package com.example.demonbuilderv2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.demonbuilderv2.db.DemonDatabase;

@Entity(tableName = DemonDatabase.USERS_TABLE)
public class Users {

    @PrimaryKey(autoGenerate = true)
    private int mUserID;
    private String mUsername;
    private String mPassword;
    private String mIsAdmin;

    public Users(String username, String password, String isAdmin) {
        mUsername = username;
        mPassword = password;
        mIsAdmin = isAdmin;
    }

    public String getUsername() {
        return mUsername;
    }

    @Override
    public String toString() {
        return "Users{" +
                "mUserID='" + mUserID + '\'' +
                "mUsername='" + mUsername + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mIsAdmin='" + mIsAdmin + '\'' +
                '}';
    }

    public int getUserID() {
        return mUserID;
    }

    public void setUserID(int userID) {
        mUserID = userID;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getIsAdmin() {
        return mIsAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        mIsAdmin = isAdmin;
    }


}
