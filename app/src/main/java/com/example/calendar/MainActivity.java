package com.example.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.calendar.DataBase.RoutineTable.RoutineEntity;
import com.example.calendar.DataBase.RoutineTable.RoutineRepository;
import com.example.calendar.DataBase.TasksTable.TaskEntity;
import com.example.calendar.DataBase.TasksTable.TasksRepository;
import com.example.calendar.Templates.MainTemplate;
import com.example.calendar.Templates.StaticMethods;
import com.example.calendar.TodayTasks.NowData;
import com.example.calendar.TodayTasks.Task;
import com.example.calendar.TodayTasks.TodayTasksAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends MainTemplate {
    private TasksRepository tasksRepository;
    private RoutineRepository routineRepository;
    private List<RoutineEntity> routineTasksList;
    private List<TaskEntity> timeTasksList;
    private List<Task> tasksList;
    private ListView tasksLayout;

    private boolean emptyMessageShown = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tasksLayout = findViewById(R.id.mainTodayTasks);
        tasksRepository = ((MyApplication) getApplication()).getTasksRepository();
        routineRepository = ((MyApplication) getApplication()).getRoutineRepository();

        getTasks();
    }

    private void getTasks() {
        routineRepository.getTasksForDay(NowData.getDayOfWeek())
                .observe(this, tasks -> {
                    routineTasksList = tasks;
                    updateUI();
                });

        tasksRepository.getByDate(StaticMethods.getStringFromDate(NowData.getDate()))
                .observe(this, tasks -> {
                    timeTasksList = tasks;
                    updateUI();
                });
    }

    private void updateUI() {
        tasksList = new ArrayList<>();

        if (routineTasksList != null) {
            for (RoutineEntity item : routineTasksList) {
                tasksList.add(new Task(
                        item.getTitle(),
                        null,
                        NowData.fromHourMinute(item.getHour(), 0),
                        NowData.fromHourMinute(item.getHour() + 1, 0),
                        true
                ));
            }
        }

        if (timeTasksList != null) {
            for (TaskEntity item : timeTasksList) {
                if (item.getStart() != null && !item.getStart().isEmpty()) {
                    tasksList.add(new Task(
                            item.getTitle(),
                            item.getDescription(),
                            StaticMethods.getTimeFromString(item.getStart()),
                            StaticMethods.getTimeFromString(item.getEnd()),
                            false
                    ));
                }
            }
        }

        tasksLayout.setAdapter(null);

        if (!tasksList.isEmpty()) {
            tasksList.sort(Comparator.comparing(Task::getTimeStart));
            tasksLayout.setAdapter(new TodayTasksAdapter(this, tasksList));
        } else {
            if (!emptyMessageShown) {
                View emptyView = LayoutInflater.from(this)
                        .inflate(R.layout.empty_slot, tasksLayout, false);
                TextView name = emptyView.findViewById(R.id.nameEmptyItem);
                name.setText("No Tasks Yet");
                name.setTextSize(36);

                tasksLayout.addHeaderView(emptyView);
                emptyMessageShown = true;
            }
        }
    }
}
