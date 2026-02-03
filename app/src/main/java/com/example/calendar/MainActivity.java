package com.example.calendar;

import android.os.Bundle;

import com.example.calendar.Templates.MainTemplate;

public class MainActivity extends MainTemplate {

    TodayData todayData;
    TasksController tasksController;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        todayData = new TodayData();
        tasksController = new TasksController(todayData);
    }
}
