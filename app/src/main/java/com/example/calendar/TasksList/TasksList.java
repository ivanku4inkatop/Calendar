package com.example.calendar.TasksList;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.calendar.DataBaseManager;
import com.example.calendar.R;

public class TasksList extends AppCompatActivity {

    ListView tasksListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tasks_list);

        tasksListView = findViewById(R.id.tasksListView);

        loadFromDB();

        TasksAdapter tasksAdapter = new TasksAdapter(this, TaskItem.getTasksArray());
        tasksListView.setAdapter(tasksAdapter);
    }


    private void loadFromDB(){
        DataBaseManager dataBaseManager = DataBaseManager.instanceOfDataBase(this);
        dataBaseManager.populateTaskListArray();
    }
}