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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemonSignUpActivity extends AppCompatActivity {
    private EditText editTextUsernameSignUp;
    private EditText editTextPasswordSignUp;
    private Button buttonSignUp;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demon_sign_up);

        editTextUsernameSignUp = findViewById(R.id.editTextUsernameSignUp);
        editTextPasswordSignUp = findViewById(R.id.editTextPasswordSignUp);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        executorService = Executors.newSingleThreadExecutor();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }
    private void signUpUser() {
        String username = editTextUsernameSignUp.getText().toString().trim();
        String password = editTextPasswordSignUp.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }
        executorService.execute(() -> {
            UsersDAO dao = DemonDatabase.getInstance(getApplicationContext()).UsersDAO();

            // Creating a new user object
            Users newUser = new Users(username, password, false);
            newUser.setUsername(username);
            newUser.setPassword(password);

            try {
                // Attempt to insert the new user
                dao.insert(newUser);

                // If no exception was thrown, assume the insert was successful
                runOnUiThread(() -> {
                    Toast.makeText(DemonSignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                    // Navigate to the landing page or login page
                    navigateToLandingPage(username);
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(DemonSignUpActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void navigateToLandingPage(String username) {
        Intent intent = new Intent(this, DemonLandingPageActivity.class);
        startActivity(intent);
        finish(); // Closes the current activity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}