package com.example.demonbuilderv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demonbuilderv2.db.DemonDatabase;
import com.example.demonbuilderv2.db.UsersDAO;

public class DemonProfileActivity extends AppCompatActivity {

    private EditText editTextAge, editTextHeight, editTextWeight;
    private Button buttonSaveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demon_profile);

        editTextAge = findViewById(R.id.editTextAge);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        buttonSaveProfile = findViewById(R.id.buttonSaveProfile);


        buttonSaveProfile.setOnClickListener(view -> saveUserProfile());

        Button saveProfileButton = findViewById(R.id.buttonSaveProfile);
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserProfile();
            }
        });

    }
    private void saveUserProfile() {
        String username = getCurrentUsername();
        String password = getCurrentPassword();

        DemonDatabase db = DemonDatabase.getInstance(getApplicationContext());
        UsersDAO userDAO = db.UsersDAO();

        new Thread(() -> {
            Users user = userDAO.getUserByUsernameAndPassword(username, password);

            if (user != null) {
                // The user is authenticated, proceed to update their profile
                runOnUiThread(() -> updateProfileDetails(user));
            } else {
                runOnUiThread(() -> {
                    // Show a message to the user
                    Toast.makeText(DemonProfileActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }


    private String getCurrentUsername() {
        SharedPreferences prefs = this.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        return prefs.getString("username", null);
    }

    private String getCurrentPassword() {
        SharedPreferences prefs = this.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        return prefs.getString("password", null);
    }
    private void updateProfileDetails(Users user) {
        int age = Integer.parseInt(editTextAge.getText().toString());
        float height = Float.parseFloat(editTextHeight.getText().toString());
        float weight = Float.parseFloat(editTextWeight.getText().toString());

        // Setting the new profile details
        user.setAge(age);
        user.setHeight(height);
        user.setWeight(weight);


        new Thread(() -> {
            DemonDatabase db = DemonDatabase.getInstance(getApplicationContext());


            UsersDAO userDAO = db.UsersDAO();

            if (user != null) {
                user.setAge(age);
                user.setHeight(height);
                user.setWeight(weight);

                userDAO.update(user);
                runOnUiThread(() -> Toast.makeText(DemonProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}