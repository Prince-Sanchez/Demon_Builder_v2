package com.example.demonbuilderv2.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.demonbuilderv2.Logs;

import java.util.List;

@Dao
public interface LogsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertWorkoutLog(Logs workoutLog);

    @Query("UPDATE logs SET achievements = :achievements WHERE id = :userId AND date = :date")
    void updateAchievementsForUserOnDate(int userId, String date, String achievements);

    @Query("SELECT * FROM logs ORDER BY date DESC")
    List<Logs> getAllWorkoutLogs();


}
