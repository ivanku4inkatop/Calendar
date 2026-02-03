package com.example.calendar;

import android.app.Application;

import com.example.calendar.DataBase.RoutineTable.RoutineRepository;
import com.example.calendar.DataBase.TasksTable.TasksRepository;

public class MyApplication extends Application {
    private TasksRepository tasksRepository;
    private RoutineRepository routineRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        tasksRepository = new TasksRepository(getApplicationContext());
        routineRepository = new RoutineRepository(getApplicationContext());
    }

    public TasksRepository getTasksRepository() {return tasksRepository;}
    public RoutineRepository getRoutineRepository() {return routineRepository;}

}
