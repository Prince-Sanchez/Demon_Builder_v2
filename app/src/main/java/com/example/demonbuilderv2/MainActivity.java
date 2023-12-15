package com.example.demonbuilderv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demonbuilderv2.db.DemonDatabase;
import com.example.demonbuilderv2.db.UsersDAO;

public class MainActivity extends AppCompatActivity {

    private Button button2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Login Activity
                Intent loginIntent = new Intent(MainActivity.this, DemonLoginActivity.class);
                startActivity(loginIntent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Sign Up Activity
                Intent signUpIntent = new Intent(MainActivity.this, DemonSignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        initializePredefinedUsers();
    }
    private void initializePredefinedUsers() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);

        if (isFirstRun) {
            // Insert predefined users on a background thread
            AsyncTask.execute(() -> {
                DemonDatabase db = DemonDatabase.getInstance(getApplicationContext());
                UsersDAO usersDAO = db.UsersDAO();
                usersDAO.insert(new Users("testuser1", "testuser1", false));
                usersDAO.insert(new Users("admin2", "admin2", true));
            });

            // Update SharedPreferences since the first run is complete
            prefs.edit().putBoolean("isFirstRun", false).apply();
        }
    }
}