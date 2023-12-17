package com.example.demonbuilderv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demonbuilderv2.db.DemonDatabase;
import com.example.demonbuilderv2.db.ExercisesDAO;

public class DemonAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demon_admin);

        EditText editTextSets = findViewById(R.id.editTextSets);
        EditText editTextReps = findViewById(R.id.editTextReps);
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextMuscleGroup = findViewById(R.id.editTextMuscleGroup);


        String setsString = editTextSets.getText().toString();
        int sets;
        if (!setsString.isEmpty()) {
            sets = Integer.parseInt(setsString);
        } else {
            sets = 0;
        }
        String repsString = editTextReps.getText().toString();
        int reps;
        if (!repsString.isEmpty()) {
            reps = Integer.parseInt(repsString);
        } else {
            reps = 0;
        }
        String name = editTextName.getText().toString();
        String muscleGroup = editTextMuscleGroup.getText().toString();




        Button addExerciseButton = findViewById(R.id.addExerciseButton);
        addExerciseButton.setOnClickListener(view -> {

            Exercises newExercise = new Exercises(sets, reps, name, muscleGroup);
            ExercisesDAO exercisesDAO = DemonDatabase.getInstance(getApplicationContext()).ExercisesDAO();
            new Thread(() -> {
                exercisesDAO.insert(newExercise);
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Exercise added", Toast.LENGTH_SHORT).show());
            }).start();
        });
    }
}