package com.example.demonbuilderv2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercises")
public class Exercises {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private int sets;
    private int reps;

    public String name;
    public String muscleGroup;

    public Exercises(int sets, int reps, String name, String muscleGroup) {
        this.sets = sets;
        this.reps = reps;
        this.name = name;
        this.muscleGroup = muscleGroup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }
}

