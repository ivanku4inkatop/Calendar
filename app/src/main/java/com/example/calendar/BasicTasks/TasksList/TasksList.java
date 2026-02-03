package com.example.calendar.BasicTasks.TasksList;

import android.os.Bundle;
import android.widget.ListView;

import com.example.calendar.DataBase.TasksTable.TaskEntity;
import com.example.calendar.DataBase.TasksTable.TasksRepository;
import com.example.calendar.MyApplication;
import com.example.calendar.R;
import com.example.calendar.Templates.MainTemplate;

import java.util.ArrayList;
import java.util.List;

public class TasksList extends MainTemplate {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tasks_list;
    }

    ListView tasksListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        tasksListView = findViewById(R.id.tasksListView);

        TasksRepository repo = ((MyApplication) getApplication()).getTasksRepository();
        repo.getAll().observe(this, tasks -> {
            List<TaskEntity> taskList = (tasks != null) ? tasks : new ArrayList<>();
            TasksAdapter adapter = new TasksAdapter(this, taskList);
            tasksListView.setAdapter(adapter);
        });

    }


}