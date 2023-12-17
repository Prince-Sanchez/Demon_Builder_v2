package com.example.demonbuilderv2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.demonbuilderv2.db.DemonDatabase;
import com.example.demonbuilderv2.db.ExercisesDAO;
import com.example.demonbuilderv2.db.LogsDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    public void showSetsRepsDialog(Exercises exercise, OnSetsRepsEnteredListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Sets and Reps");

        // Create EditTexts programmatically for sets and reps
        final EditText setsEditText = new EditText(this);
        setsEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        setsEditText.setHint("Sets");

        final EditText repsEditText = new EditText(this);
        repsEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        repsEditText.setHint("Reps");

        // Create a LinearLayout to hold the EditTexts
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(setsEditText);
        layout.addView(repsEditText);

        // Add padding to the LinearLayout
        int padding = (int) (16 * getResources().getDisplayMetrics().density);
        layout.setPadding(padding, padding, padding, padding);

        builder.setView(layout);

        // Set up the buttons for the AlertDialog
        builder.setPositiveButton("Save", (dialogInterface, i) -> {
            try {
                int sets = Integer.parseInt(setsEditText.getText().toString());
                int reps = Integer.parseInt(repsEditText.getText().toString());
                listener.onSetsRepsEntered(exercise, sets, reps);
            } catch (NumberFormatException e) {
                // Handle exception if user enters invalid number
                Toast.makeText(this, "Please enter valid numbers for sets and reps.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public interface OnSetsRepsEnteredListener {
        void onSetsRepsEntered(Exercises exercise, int sets, int reps);
    }
    private final OnSetsRepsEnteredListener setsRepsEnteredListener = new OnSetsRepsEnteredListener() {
        @Override
        public void onSetsRepsEntered(Exercises exercise, int sets, int reps) {
            saveWorkoutLog(exercise, sets, reps);
        }
    };
    private void saveWorkoutLog(Exercises exercise, int sets, int reps) {
        // This should be run on a background thread
        new Thread(() -> {
            // Create an instance of the workout log entry
            Logs workoutLog = new Logs();
            workoutLog.setExerciseName(exercise.getName());
            workoutLog.setDate(String.valueOf(new Date()));

            DemonDatabase db = DemonDatabase.getInstance(getApplicationContext());

            LogsDAO LogsDAO = db.LogsDAO();

            // Using DAO instance to call the insert method
            com.example.demonbuilderv2.db.LogsDAO logsDAO;
            LogsDAO.insertWorkoutLog(workoutLog);

            // Remember to run UI operations on the main thread
            // For example, if you want to show a Toast or update the UI
            runOnUiThread(() -> {
                // Update UI or show confirmation message
                Toast.makeText(getApplicationContext(), "Workout logged successfully", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}