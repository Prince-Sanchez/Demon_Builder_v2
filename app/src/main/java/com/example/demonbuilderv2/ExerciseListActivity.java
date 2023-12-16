package com.example.demonbuilderv2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.example.demonbuilderv2.db.DemonDatabase;
import com.example.demonbuilderv2.db.ExercisesDAO;
import com.example.demonbuilderv2.Exercises;

public class ExerciseListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private List<Exercises> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        recyclerView = findViewById(R.id.recyclerViewExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        exerciseList = new ArrayList<>();
        adapter = new ExerciseAdapter(exerciseList);
        recyclerView.setAdapter(adapter);

        // Retrieve the muscle group from the intent
        String muscleGroup = getIntent().getStringExtra("MUSCLE_GROUP");

        // Load exercises for the given muscle group
        loadExercises(muscleGroup);
    }

    private void loadExercises(String muscleGroup) {
        // Database operations should be done on a background thread
        new Thread(() -> {
            ExercisesDAO dao = DemonDatabase.getInstance(getApplicationContext()).ExercisesDAO();
            List<Exercises> exercises = dao.getExercisesForMuscleGroup(muscleGroup);
            runOnUiThread(() -> {
                exerciseList.clear();
                exerciseList.addAll(exercises);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    public static class ExerciseAdapter extends RecyclerView.Adapter<ExerciseViewHolder> {

        private List<Exercises> exerciseList; // The data for your list

        public ExerciseAdapter(List<Exercises> exercisesList) {
            this.exerciseList = exercisesList;
        }

        @NonNull
        @Override
        public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ExerciseViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
            Exercises exercise = exerciseList.get(position);
            holder.textView.setText(exercise.getName());
        }

        @Override
        public int getItemCount() {
            return exerciseList != null ? exerciseList.size() : 0;
        }

        public void setExercises(List<Exercises> exercises) {
            this.exerciseList = exercises;
        }
    }
}