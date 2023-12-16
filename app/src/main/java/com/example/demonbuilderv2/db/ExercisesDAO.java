package com.example.demonbuilderv2.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.demonbuilderv2.Exercises;

import java.util.List;
@Dao
public interface ExercisesDAO {

    @Insert
    void insert(Exercises exercises);

    @Query("SELECT * FROM exercises WHERE muscleGroup = :muscleGroup")
    List<Exercises> getExercisesForMuscleGroup(String muscleGroup);

    @Delete
    void delete(Exercises exercises);

}
