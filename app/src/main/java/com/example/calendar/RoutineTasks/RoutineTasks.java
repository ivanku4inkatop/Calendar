package com.example.calendar.RoutineTasks;

import android.os.Bundle;
import android.widget.ListView;

import androidx.lifecycle.ViewModelProvider;

import com.example.calendar.DataBase.RoutineTable.DayCount;
import com.example.calendar.R;
import com.example.calendar.Templates.MainTemplate;
import java.util.List;

public class RoutineTasks extends MainTemplate {
    private RoutineTasksViewModel viewModel;
    private ListView listView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_routime_tasks;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = findViewById(R.id.daysListView);

        viewModel = new ViewModelProvider(this)
                .get(RoutineTasksViewModel.class);

        viewModel.tasksCountPerWeek().observe(this, counts -> {
            DaysAdapter adapter = new DaysAdapter(this, counts);
            listView.setAdapter(adapter);
        });
    }
}