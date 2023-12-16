package com.example.demonbuilderv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.demonbuilderv2.db.DemonDatabase;
import com.example.demonbuilderv2.db.UsersDAO;

public class DemonLandingPageActivity extends AppCompatActivity {

    private TextView textViewUsername;
    private TextView textViewAdminStatus;
    private Button btnAdminArea;
    private Button btnLogout;
    private Button btnDeleteAccount;
    private Button btnExercises;
    private Button btnWorkouts;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demon_landing_page);

        textViewUsername = findViewById(R.id.textViewUsername);
        textViewAdminStatus = findViewById(R.id.textViewAdminStatus);
        btnAdminArea = findViewById(R.id.btnAdminArea);
        btnLogout = findViewById(R.id.btnLogout);
        btnDeleteAccount = findViewById((R.id.btnDeleteAccount));
        btnExercises = findViewById((R.id.btnExercises));
        btnWorkouts = findViewById((R.id.btnWorkouts));

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        // Retrieve username from SharedPreferences
        username = sharedPreferences.getString("username", null);
        // Check if the user is logged in
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        btnExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemonLandingPageActivity.this, DemonExercisesActivity.class);
                startActivity(intent);
            }
        });

        btnWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemonLandingPageActivity.this, DemonWorkoutActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear user session data from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // This will clear all data
                editor.apply();

                // Navigate back to the login screen
                Intent intent = new Intent(DemonLandingPageActivity.this, DemonLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clears the activity stack
                startActivity(intent);
                finish(); // Close the current activity
            }
        });
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUserAccount();
            }
        });

        // Get the data from the intent
        String username = getIntent().getStringExtra("USERNAME");
        boolean isAdmin = getIntent().getBooleanExtra("IS_ADMIN", false);

        // Update the UI with the username
        textViewUsername.setText(username);
        // Update the UI for the admin status
        textViewAdminStatus.setText("Admin: " + (isAdmin ? "Yes" : "No"));

        // Make the admin area button visible if the user is an admin
        if (isAdmin) {
            btnAdminArea.setVisibility(View.VISIBLE);
        } else {
            btnAdminArea.setVisibility(View.INVISIBLE); // or View.GONE to not take up any space in layout
        }
    }
    private void deleteUserAccount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DemonDatabase db = DemonDatabase.getInstance(getApplicationContext());
                UsersDAO dao = db.UsersDAO();
                Users user = dao.findUserByUsername(username); // Assuming you have a method to find user by username
                if (user != null) {
                    dao.delete(user); // Delete the user
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Clear shared preferences or any other session data
                            clearSessionData();
                            // Redirect to login screen or any other appropriate screen
                            redirectToLogin();
                        }
                    });
                }
            }
        }).start();
    }
    private void clearSessionData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private void redirectToLogin() {
        Intent intent = new Intent(DemonLandingPageActivity.this, DemonLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}