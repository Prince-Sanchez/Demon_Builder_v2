package com.example.demonbuilderv2;

import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.Gravity;
import android.graphics.Typeface;
import android.util.TypedValue;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    TextView setsRepsTextView;

    public ExerciseViewHolder(ViewGroup parent) {
        super(new TextView(parent.getContext()));

        textView = (TextView) itemView;
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        textView.setPadding(16, 16, 16, 16);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
    }
    public void bind(Exercises exercise) {
        textView.setText(exercise.getName());
        setsRepsTextView.setText(exercise.getSets() + " sets x " + exercise.getReps() + " reps");
    }
}