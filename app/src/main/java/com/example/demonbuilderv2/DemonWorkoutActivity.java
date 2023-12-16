package com.example.demonbuilderv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.demonbuilderv2.db.DemonDatabase;
import com.example.demonbuilderv2.db.ExercisesDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class DemonWorkoutActivity extends AppCompatActivity {

    private Spinner spinnerWorkoutType;
    private RecyclerView recyclerView;
    private ExerciseListActivity.ExerciseAdapter exerciseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demon_workout);

        recyclerView = findViewById(R.id.recyclerViewExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        exerciseAdapter = new ExerciseListActivity.ExerciseAdapter(new ArrayList<>()); // Initialize with an empty list
        recyclerView.setAdapter(exerciseAdapter);

        spinnerWorkoutType = findViewById(R.id.spinnerWorkoutType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.workout_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkoutType.setAdapter(adapter);

        spinnerWorkoutType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedWorkoutType = parent.getItemAtPosition(position).toString();
                if (!selectedWorkoutType.equals("Select Workout Type")) {
                    generateWorkout(selectedWorkoutType);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
            private void generateWorkout(String workoutType) {
                new Thread(() -> {
                    List<Exercises> workoutExercises = new ArrayList<>();
                    ExercisesDAO exerciseDao = DemonDatabase.getInstance(getApplicationContext()).ExercisesDAO();
                    Random random = new Random();

                    // Fetch all exercises for the muscle groups associated with the workout type
                    List<Exercises> allExercises = new ArrayList<>();
                    if ("Push".equals(workoutType)) {
                        allExercises.addAll(exerciseDao.getExercisesForMuscleGroup("Chest"));
                        allExercises.addAll(exerciseDao.getExercisesForMuscleGroup("Triceps"));
                        allExercises.addAll(exerciseDao.getExercisesForMuscleGroup("Shoulders"));
                        allExercises.addAll(exerciseDao.getExercisesForMuscleGroup("Neck"));
                    } else if ("Pull".equals(workoutType)) {
                        allExercises.addAll(exerciseDao.getExercisesForMuscleGroup("Back"));
                        allExercises.addAll(exerciseDao.getExercisesForMuscleGroup("Biceps"));
                        allExercises.addAll(exerciseDao.getExercisesForMuscleGroup("Forearms"));
                    } else if ("Lower".equals(workoutType)) {
                        allExercises.addAll(exerciseDao.getExercisesForMuscleGroup("Quads"));
                        allExercises.addAll(exerciseDao.getExercisesForMuscleGroup("Hamstrings"));
                        allExercises.addAll(exerciseDao.getExercisesForMuscleGroup("Calves"));
                        allExercises.addAll(exerciseDao.getExercisesForMuscleGroup("Glutes"));
                        allExercises.addAll(exerciseDao.getExercisesForMuscleGroup("Abductors"));
                        allExercises.addAll(exerciseDao.getExercisesForMuscleGroup("Adductors"));
                    }

                    // Shuffle the list of all exercises
                    Collections.shuffle(allExercises, random);

                    // Select a random subset from the shuffled list (7 or 8 exercises)
                    int numberOfExercisesToSelect = Math.min(allExercises.size(), 7); // or 8 if you prefer
                    workoutExercises.addAll(allExercises.subList(0, numberOfExercisesToSelect));

                    runOnUiThread(() -> updateUIWithExercises(workoutExercises));
                }).start();
            }
            private void updateUIWithExercises(List<Exercises> exercises) {
                exerciseAdapter.setExercises(exercises);
                exerciseAdapter.notifyDataSetChanged();
            }
        });
    }
}