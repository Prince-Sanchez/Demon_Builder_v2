package com.example.demonbuilderv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DemonWaterLog extends AppCompatActivity {

    private EditText editTextWaterIntake;
    private Button buttonLogWater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demon_water_log);

        editTextWaterIntake = findViewById(R.id.editTextWaterIntake);
        buttonLogWater = findViewById(R.id.buttonLogWater);

        buttonLogWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logWaterIntake();
            }
        });
    }
    private void logWaterIntake() {
        String waterIntakeString = editTextWaterIntake.getText().toString();
        if (!waterIntakeString.isEmpty()) {
            float waterIntake = Float.parseFloat(waterIntakeString);
            // Show confirmation message
            Toast.makeText(this, "Logged " + waterIntake + " ounces of water", Toast.LENGTH_SHORT).show();
            // Reset the EditText for new input
            editTextWaterIntake.setText("");
        } else {
            // Notify the user to enter some value
            Toast.makeText(this, "Please enter the amount of water", Toast.LENGTH_SHORT).show();
        }
    }
}
