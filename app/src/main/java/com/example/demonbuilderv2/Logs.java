package com.example.demonbuilderv2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "logs")
public class Logs {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String exerciseName;
    private String date; // You could also use a Date type with a TypeConverter
    private String achievements;

    public Logs() {
        // This constructor is empty
    }

    public Logs(String date, String achievements) {
        this.date = date;
        this.achievements = achievements;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }
}
