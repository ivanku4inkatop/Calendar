package com.example.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.calendar.TasksList.TasksList;
import com.example.calendar.Templates.MainTemplate;

import java.util.Calendar;

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


        Button listBtn = findViewById(R.id.tasksListButton);

        todayData = new TodayData();
        tasksController = new TasksController(todayData);

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TasksList.class);
                startActivity(intent);
            }
        });
    }
}
