package com.example.calendar;

import android.app.Application;

import com.example.calendar.DataBase.TasksTable.TasksRepository;

public class MyApplication extends Application {
    private TasksRepository tasksRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        tasksRepository = new TasksRepository(getApplicationContext());
    }

    public TasksRepository getTasksRepository() {
        return tasksRepository;
    }
}
