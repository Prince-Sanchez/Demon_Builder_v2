package com.example.demonbuilderv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.demonbuilderv2.db.DemonDatabase;
import com.example.demonbuilderv2.db.LogsDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DemonLogActivity extends AppCompatActivity {

    private TextView tvWorkoutStreak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demon_log);

        tvWorkoutStreak = findViewById(R.id.tvWorkoutStreak);
        calculateWorkoutStreak();
    }

    private void calculateWorkoutStreak() {
        new Thread(() -> {
            LogsDAO logsDAO = DemonDatabase.getInstance(getApplicationContext()).LogsDAO();
            List<Logs> workoutLogs = logsDAO.getAllWorkoutLogs();

            int streak = calculateStreak(workoutLogs);

            runOnUiThread(() -> tvWorkoutStreak.setText("Workout Streak: " + streak + " Days"));
        }).start();
    }

    private int calculateStreak(List<Logs> logs) {
        if (logs == null || logs.isEmpty()) {
            return 0;
        }

        Collections.sort(logs, (log1, log2) -> log2.getDate().compareTo(log1.getDate()));
        int streak = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();

        // Start from yesterday and go backwards in time
        cal.add(Calendar.DATE, -1);
        Date lastLogDate = cal.getTime();

        for (int i = logs.size() - 1; i >= 0; i--) { // Iterating from most recent to oldest
            try {
                Date logDate = sdf.parse(logs.get(i).getDate());
                if (!logDate.before(lastLogDate)) {
                    streak++; // If the log date is not before the last log date, increment streak
                    cal.add(Calendar.DATE, -1);
                    lastLogDate = cal.getTime();
                } else {
                    break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return streak;
    }

    private Date addDaysToDate(String date, int days) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
}
