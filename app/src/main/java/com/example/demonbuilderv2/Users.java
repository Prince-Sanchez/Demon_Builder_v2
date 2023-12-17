package com.example.demonbuilderv2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.demonbuilderv2.db.DemonDatabase;

@Entity(tableName = "users")
public class Users {

    @PrimaryKey(autoGenerate = true)
    private int mUserID;
    private String mUsername;
    private String mPassword;
    private boolean mIsAdmin;

    private int age;
    private float height;
    private float weight;

    public Users(String username, String password, boolean isAdmin) {
        mUsername = username;
        mPassword = password;
        mIsAdmin = isAdmin;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getUserID() {
        return mUserID;
    }

    public void setUserID(int userID) {
        mUserID = userID;
    }

    public String getUsername() {
        return mUsername;
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

    public boolean getIsAdmin() {
        return mIsAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        mIsAdmin = isAdmin;
    }


}
