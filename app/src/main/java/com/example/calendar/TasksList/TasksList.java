package com.example.calendar.TasksList;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calendar.DataBase.TasksTable.TasksRepository;
import com.example.calendar.MyApplication;
import com.example.calendar.R;

public class TasksList extends AppCompatActivity {

    ListView tasksListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tasks_list);

        tasksListView = findViewById(R.id.tasksListView);

        TasksRepository repo = ((MyApplication) getApplication()).getTasksRepository();
        repo.getAll().observe(this, tasks -> {
            TasksAdapter adapter = new TasksAdapter(this, tasks);
            tasksListView.setAdapter(adapter);
        });

    }


}