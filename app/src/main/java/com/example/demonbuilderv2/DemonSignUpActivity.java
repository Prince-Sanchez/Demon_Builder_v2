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
                loginUser();
            }
        });
    }
    private void loginUser() {
        String username = editTextUsernameSignUp.getText().toString().trim();
        String password = editTextPasswordSignUp.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        executorService.execute(() -> {
            UsersDAO dao = DemonDatabase.getInstance(getApplicationContext()).UsersDAO();
            Users user = dao.getUserByUsernameAndPassword(username, password);

            runOnUiThread(() -> {
                if (user != null) {
                    Toast.makeText(DemonSignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                    // Handle successful login here, e.g., navigate to another activity
                    SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username); // 'username' is the string of the logged-in user
                    editor.apply();

                    Intent intent = new Intent(DemonSignUpActivity.this, DemonLandingPageActivity.class);
                    intent.putExtra("USERNAME", username);
                    intent.putExtra("IS_ADMIN", user.getIsAdmin());
                    startActivity(intent);
                    finish(); // Close the login activity so the user can't go back to it
                } else {
                    Toast.makeText(DemonSignUpActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}