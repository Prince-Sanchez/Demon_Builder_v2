package com.example.demonbuilderv2.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.demonbuilderv2.Exercises;
import com.example.demonbuilderv2.Logs;
import com.example.demonbuilderv2.Users;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Users.class, Exercises.class, Logs.class}, version = 5)
public abstract class DemonDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "demon_database.db";

    private static volatile DemonDatabase instance;
    private static final Object LOCK = new Object();

    public abstract UsersDAO UsersDAO();
    public abstract ExercisesDAO ExercisesDAO();
    public abstract LogsDAO LogsDAO();

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Using Executors to insert users
            ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(3);
            databaseWriteExecutor.execute(() -> {
                // Get DAO and insert users and exercises and logs
                UsersDAO usersDao = instance.UsersDAO();
                ExercisesDAO exerciseDao = instance.ExercisesDAO();
                LogsDAO logsDAO = instance.LogsDAO();
                //Inserting predefined users
                usersDao.insert(new Users("testuser1", "testuser1", false));
                usersDao.insert(new Users("admin2", "admin2", true));
                //Inserting exercises
                // Chest Exercises
                exerciseDao.insert(new Exercises(4, 8,"Bench Press", "Chest"));
                exerciseDao.insert(new Exercises(4, 8,"Push Up", "Chest"));
                exerciseDao.insert(new Exercises(4, 8,"Incline Bench Press", "Chest"));
                exerciseDao.insert(new Exercises(4, 8,"Chest Fly", "Chest"));
                exerciseDao.insert(new Exercises(4, 8,"Dumbbell Press", "Chest"));

                // Back Exercises
                exerciseDao.insert(new Exercises(4, 8,"Pull Up", "Back"));
                exerciseDao.insert(new Exercises(4, 8,"Bent Over Row", "Back"));
                exerciseDao.insert(new Exercises(4, 8,"Deadlift", "Back"));
                // Back Exercises
                exerciseDao.insert(new Exercises(4, 8, "Lat Pulldown", "Back"));
                exerciseDao.insert(new Exercises(4, 8, "Seated Row", "Back"));

                // Biceps Exercises
                exerciseDao.insert(new Exercises(4, 8, "Bicep Curl", "Biceps"));
                exerciseDao.insert(new Exercises(4, 8, "Hammer Curl", "Biceps"));
                exerciseDao.insert(new Exercises(4, 8, "Preacher Curl", "Biceps"));
                exerciseDao.insert(new Exercises(4, 8, "Concentration Curl", "Biceps"));
                exerciseDao.insert(new Exercises(4, 8, "Cable Curl", "Biceps"));

                // Triceps Exercises
                exerciseDao.insert(new Exercises(4, 8, "Tricep Dip", "Triceps"));
                exerciseDao.insert(new Exercises(4, 8, "Skull Crusher", "Triceps"));
                exerciseDao.insert(new Exercises(4, 8, "Tricep Kickback", "Triceps"));
                exerciseDao.insert(new Exercises(4, 8, "Overhead Tricep Extension", "Triceps"));
                exerciseDao.insert(new Exercises(4, 8, "Close Grip Bench Press", "Triceps"));

                // Forearms Exercises
                exerciseDao.insert(new Exercises(4, 8, "Wrist Curl", "Forearms"));
                exerciseDao.insert(new Exercises(4, 8, "Reverse Wrist Curl", "Forearms"));
                exerciseDao.insert(new Exercises(4, 8, "Farmer’s Walk", "Forearms"));
                exerciseDao.insert(new Exercises(4, 8, "Hammer Curl", "Forearms")); // Note: Hammer Curl might be duplicated since it's also listed under Biceps
                exerciseDao.insert(new Exercises(4, 8, "Wrist Roller", "Forearms"));

                // Abs Exercises
                exerciseDao.insert(new Exercises(4, 8, "Crunch", "Abs"));
                exerciseDao.insert(new Exercises(4, 8, "Plank", "Abs")); // Planks are usually timed, consider using seconds for duration
                exerciseDao.insert(new Exercises(4, 8, "Russian Twist", "Abs"));
                exerciseDao.insert(new Exercises(4, 8, "Leg Raise", "Abs"));
                exerciseDao.insert(new Exercises(4, 8, "Bicycle Crunch", "Abs"));

                // Shoulders Exercises
                exerciseDao.insert(new Exercises(4, 8, "Overhead Press", "Shoulders"));
                exerciseDao.insert(new Exercises(4, 8, "Lateral Raise", "Shoulders"));
                exerciseDao.insert(new Exercises(4, 8, "Front Raise", "Shoulders"));
                exerciseDao.insert(new Exercises(4, 8, "Shrugs", "Shoulders")); // Note: Shrugs might be duplicated since it's also listed under Trapezius
                exerciseDao.insert(new Exercises(4, 8, "Upright Row", "Shoulders"));

                // Quads Exercises
                exerciseDao.insert(new Exercises(4, 8, "Squat", "Quads"));
                exerciseDao.insert(new Exercises(4, 8, "Lunge", "Quads"));
                exerciseDao.insert(new Exercises(4, 8, "Leg Press", "Quads"));
                exerciseDao.insert(new Exercises(4, 8, "Leg Extension", "Quads"));
                exerciseDao.insert(new Exercises(4, 8, "Front Squat", "Quads"));

                // Hamstrings Exercises
                exerciseDao.insert(new Exercises(4, 8, "Deadlift", "Hamstrings"));
                exerciseDao.insert(new Exercises(4, 8, "Leg Curl", "Hamstrings"));
                exerciseDao.insert(new Exercises(4, 8, "Good Morning", "Hamstrings"));
                exerciseDao.insert(new Exercises(4, 8, "Glute-Ham Raise", "Hamstrings"));
                exerciseDao.insert(new Exercises(4, 8, "Romanian Deadlift", "Hamstrings"));

                // Glutes Exercises
                exerciseDao.insert(new Exercises(4, 8, "Squat", "Glutes"));
                exerciseDao.insert(new Exercises(4, 8, "Deadlift", "Glutes"));
                exerciseDao.insert(new Exercises(4, 8, "Lunge", "Glutes"));
                exerciseDao.insert(new Exercises(4, 8, "Hip Thrust", "Glutes"));
                exerciseDao.insert(new Exercises(4, 8, "Glute Bridge", "Glutes"));

                // Calves Exercises
                exerciseDao.insert(new Exercises(4, 8, "Standing Calf Raise", "Calves"));
                exerciseDao.insert(new Exercises(4, 8, "Seated Calf Raise", "Calves"));
                exerciseDao.insert(new Exercises(4, 8, "Leg Press Calf Raise", "Calves"));
                exerciseDao.insert(new Exercises(4, 8, "Calf Dip", "Calves"));
                exerciseDao.insert(new Exercises(4, 8, "Donkey Calf Raise", "Calves"));

                // Adductors Exercises
                exerciseDao.insert(new Exercises(4, 8, "Adductor Machine", "Adductors"));
                exerciseDao.insert(new Exercises(4, 8, "Cable Hip Adduction", "Adductors"));
                exerciseDao.insert(new Exercises(4, 8, "Side Lunge", "Adductors"));
                exerciseDao.insert(new Exercises(4, 8, "Sumo Squat", "Adductors"));
                exerciseDao.insert(new Exercises(4, 8, "Ball Squeezes", "Adductors"));

                // Abductors Exercises
                exerciseDao.insert(new Exercises(4, 8, "Abductor Machine", "Abductors"));
                exerciseDao.insert(new Exercises(4, 8, "Cable Hip Abduction", "Abductors"));
                exerciseDao.insert(new Exercises(4, 8, "Side Leg Raise", "Abductors"));
                exerciseDao.insert(new Exercises(4, 8, "Band Walks", "Abductors"));
                exerciseDao.insert(new Exercises(4, 8, "Clamshell", "Abductors"));

                // Trapezius Exercises
                exerciseDao.insert(new Exercises(4, 8, "Shrugs", "Trapezius"));
                exerciseDao.insert(new Exercises(4, 8, "Upright Row", "Trapezius"));
                exerciseDao.insert(new Exercises(4, 8, "Face Pull", "Trapezius"));
                exerciseDao.insert(new Exercises(4, 8, "Dumbbell Row", "Trapezius"));
                exerciseDao.insert(new Exercises(4, 8, "Farmer’s Walk", "Trapezius"));

                // Neck Exercises
                exerciseDao.insert(new Exercises(4, 8, "Neck Flexion", "Neck"));
                exerciseDao.insert(new Exercises(4, 8, "Neck Extension", "Neck"));
                exerciseDao.insert(new Exercises(4, 8, "Lateral Neck Flexion", "Neck"));
                exerciseDao.insert(new Exercises(4, 8, "Neck Rotation", "Neck"));
                exerciseDao.insert(new Exercises(4, 8, "Dumbbell Shrug", "Neck"));



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

