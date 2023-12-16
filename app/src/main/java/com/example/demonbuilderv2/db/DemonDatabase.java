package com.example.demonbuilderv2.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.demonbuilderv2.Exercises;
import com.example.demonbuilderv2.Users;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Users.class, Exercises.class}, version = 1)
public abstract class DemonDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "demon_database.db";

    private static volatile DemonDatabase instance;
    private static final Object LOCK = new Object();

    public abstract UsersDAO UsersDAO();
    public abstract ExercisesDAO ExercisesDAO();

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Using Executors to insert users
            ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(3);
            databaseWriteExecutor.execute(() -> {
                // Get DAO and insert users
                UsersDAO usersDao = instance.UsersDAO();
                ExercisesDAO exerciseDao = instance.ExercisesDAO();
                //Inserting predefined users
                usersDao.insert(new Users("testuser1", "testuser1", false));
                usersDao.insert(new Users("admin2", "admin2", true));
                //Inserting exercises
                // Chest Exercises
                exerciseDao.insert(new Exercises("Bench Press", "Chest"));
                exerciseDao.insert(new Exercises("Push Up", "Chest"));
                exerciseDao.insert(new Exercises("Incline Bench Press", "Chest"));
                exerciseDao.insert(new Exercises("Chest Fly", "Chest"));
                exerciseDao.insert(new Exercises("Dumbbell Press", "Chest"));

                // Back Exercises
                exerciseDao.insert(new Exercises("Pull Up", "Back"));
                exerciseDao.insert(new Exercises("Bent Over Row", "Back"));
                exerciseDao.insert(new Exercises("Deadlift", "Back"));
                exerciseDao.insert(new Exercises("Lat Pulldown", "Back"));
                exerciseDao.insert(new Exercises("Seated Row", "Back"));

                // Biceps Exercises
                exerciseDao.insert(new Exercises("Bicep Curl", "Biceps"));
                exerciseDao.insert(new Exercises("Hammer Curl", "Biceps"));
                exerciseDao.insert(new Exercises("Preacher Curl", "Biceps"));
                exerciseDao.insert(new Exercises("Concentration Curl", "Biceps"));
                exerciseDao.insert(new Exercises("Cable Curl", "Biceps"));

                // Triceps Exercises
                exerciseDao.insert(new Exercises("Tricep Dip", "Triceps"));
                exerciseDao.insert(new Exercises("Skull Crusher", "Triceps"));
                exerciseDao.insert(new Exercises("Tricep Kickback", "Triceps"));
                exerciseDao.insert(new Exercises("Overhead Tricep Extension", "Triceps"));
                exerciseDao.insert(new Exercises("Close Grip Bench Press", "Triceps"));

                // Forearms Exercises
                exerciseDao.insert(new Exercises("Wrist Curl", "Forearms"));
                exerciseDao.insert(new Exercises("Reverse Wrist Curl", "Forearms"));
                exerciseDao.insert(new Exercises("Farmer’s Walk", "Forearms"));
                exerciseDao.insert(new Exercises("Hammer Curl", "Forearms"));
                exerciseDao.insert(new Exercises("Wrist Roller", "Forearms"));

                // Abs Exercises
                exerciseDao.insert(new Exercises("Crunch", "Abs"));
                exerciseDao.insert(new Exercises("Plank", "Abs"));
                exerciseDao.insert(new Exercises("Russian Twist", "Abs"));
                exerciseDao.insert(new Exercises("Leg Raise", "Abs"));
                exerciseDao.insert(new Exercises("Bicycle Crunch", "Abs"));

                // Shoulders Exercises
                exerciseDao.insert(new Exercises("Overhead Press", "Shoulders"));
                exerciseDao.insert(new Exercises("Lateral Raise", "Shoulders"));
                exerciseDao.insert(new Exercises("Front Raise", "Shoulders"));
                exerciseDao.insert(new Exercises("Shrugs", "Shoulders"));
                exerciseDao.insert(new Exercises("Upright Row", "Shoulders"));

                // Quads Exercises
                exerciseDao.insert(new Exercises("Squat", "Quads"));
                exerciseDao.insert(new Exercises("Lunge", "Quads"));
                exerciseDao.insert(new Exercises("Leg Press", "Quads"));
                exerciseDao.insert(new Exercises("Leg Extension", "Quads"));
                exerciseDao.insert(new Exercises("Front Squat", "Quads"));

                // Hamstrings Exercises
                exerciseDao.insert(new Exercises("Deadlift", "Hamstrings"));
                exerciseDao.insert(new Exercises("Leg Curl", "Hamstrings"));
                exerciseDao.insert(new Exercises("Good Morning", "Hamstrings"));
                exerciseDao.insert(new Exercises("Glute-Ham Raise", "Hamstrings"));
                exerciseDao.insert(new Exercises("Romanian Deadlift", "Hamstrings"));

                // Glutes Exercises
                exerciseDao.insert(new Exercises("Squat", "Glutes"));
                exerciseDao.insert(new Exercises("Deadlift", "Glutes"));
                exerciseDao.insert(new Exercises("Lunge", "Glutes"));
                exerciseDao.insert(new Exercises("Hip Thrust", "Glutes"));
                exerciseDao.insert(new Exercises("Glute Bridge", "Glutes"));

                // Calves Exercises
                exerciseDao.insert(new Exercises("Standing Calf Raise", "Calves"));
                exerciseDao.insert(new Exercises("Seated Calf Raise", "Calves"));
                exerciseDao.insert(new Exercises("Leg Press Calf Raise", "Calves"));
                exerciseDao.insert(new Exercises("Calf Dip", "Calves"));
                exerciseDao.insert(new Exercises("Donkey Calf Raise", "Calves"));

                // Adductors Exercises
                exerciseDao.insert(new Exercises("Adductor Machine", "Adductors"));
                exerciseDao.insert(new Exercises("Cable Hip Adduction", "Adductors"));
                exerciseDao.insert(new Exercises("Side Lunge", "Adductors"));
                exerciseDao.insert(new Exercises("Sumo Squat", "Adductors"));
                exerciseDao.insert(new Exercises("Ball Squeezes", "Adductors"));

                // Abductors Exercises
                exerciseDao.insert(new Exercises("Abductor Machine", "Abductors"));
                exerciseDao.insert(new Exercises("Cable Hip Abduction", "Abductors"));
                exerciseDao.insert(new Exercises("Side Leg Raise", "Abductors"));
                exerciseDao.insert(new Exercises("Band Walks", "Abductors"));
                exerciseDao.insert(new Exercises("Clamshell", "Abductors"));

                // Trapezius Exercises
                exerciseDao.insert(new Exercises("Shrugs", "Trapezius"));
                exerciseDao.insert(new Exercises("Upright Row", "Trapezius"));
                exerciseDao.insert(new Exercises("Face Pull", "Trapezius"));
                exerciseDao.insert(new Exercises("Dumbbell Row", "Trapezius"));
                exerciseDao.insert(new Exercises("Farmer’s Walk", "Trapezius"));

                // Neck Exercises
                exerciseDao.insert(new Exercises("Neck Flexion", "Neck"));
                exerciseDao.insert(new Exercises("Neck Extension", "Neck"));
                exerciseDao.insert(new Exercises("Lateral Neck Flexion", "Neck"));
                exerciseDao.insert(new Exercises("Neck Rotation", "Neck"));
                exerciseDao.insert(new Exercises("Dumbbell Shrug", "Neck"));

            });
        }
    };
    public static DemonDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            DemonDatabase.class,
                            DATABASE_NAME)
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

}

