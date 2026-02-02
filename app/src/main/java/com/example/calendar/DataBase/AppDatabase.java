package com.example.calendar.DataBase;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.calendar.DataBase.TasksTable.TaskEntity;
import com.example.calendar.DataBase.TasksTable.TasksDao;

@Database(entities = {TaskEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    public abstract TasksDao tasksDao();

    public static AppDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (AppDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "calendar_db"
                            ).build();
                }
            }
        }
        return INSTANCE;
    }
}
