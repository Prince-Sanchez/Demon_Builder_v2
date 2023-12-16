package com.example.demonbuilderv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DemonExercisesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demon_exercises);

        findViewById(R.id.btnNeck).setOnClickListener(view -> openExerciseList("Neck"));
        findViewById(R.id.Trapezius).setOnClickListener(view -> openExerciseList("Trapezius"));
        findViewById(R.id.btnAbductors).setOnClickListener(view -> openExerciseList("Abductors"));
        findViewById(R.id.btnAdductors).setOnClickListener(view -> openExerciseList("Adductors"));
        findViewById(R.id.btnCalves).setOnClickListener(view -> openExerciseList("Calves"));
        findViewById(R.id.btnGlutes).setOnClickListener(view -> openExerciseList("Glutes"));
        findViewById(R.id.btnHamstrings).setOnClickListener(view -> openExerciseList("Hamstrings"));
        findViewById(R.id.btnQuads).setOnClickListener(view -> openExerciseList("Quads"));
        findViewById(R.id.btnShoulders).setOnClickListener(view -> openExerciseList("Shoulders"));
        findViewById(R.id.btnAbs).setOnClickListener(view -> openExerciseList("Abs"));
        findViewById(R.id.btnForearms).setOnClickListener(view -> openExerciseList("Forearms"));
        findViewById(R.id.btnTriceps).setOnClickListener(view -> openExerciseList("Triceps"));
        findViewById(R.id.btnBiceps).setOnClickListener(view -> openExerciseList("Biceps"));
        findViewById(R.id.btnBack).setOnClickListener(view -> openExerciseList("Back"));
        findViewById(R.id.btnChest).setOnClickListener(view -> openExerciseList("Chest"));
    }
    private void openExerciseList(String muscleGroup) {
        Intent intent = new Intent(this, ExerciseListActivity.class);
        intent.putExtra("MUSCLE_GROUP", muscleGroup);
        startActivity(intent);
    }

}