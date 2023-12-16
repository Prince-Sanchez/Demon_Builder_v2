package com.example.demonbuilderv2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercises")
public class Exercises {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String muscleGroup;

    public Exercises(String name, String muscleGroup) {
        this.name = name;
        this.muscleGroup = muscleGroup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

