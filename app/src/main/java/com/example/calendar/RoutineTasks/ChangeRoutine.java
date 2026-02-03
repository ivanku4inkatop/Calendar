package com.example.calendar.RoutineTasks;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.calendar.DataBase.RoutineTable.RoutineEntity;
import com.example.calendar.DataBase.RoutineTable.RoutineRepository;
import com.example.calendar.HoursFragment;
import com.example.calendar.MyApplication;
import com.example.calendar.R;
import com.example.calendar.Templates.MainTemplate;
import com.example.calendar.Templates.StaticMethods;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChangeRoutine extends MainTemplate {
    private static final String TAG = "ChangeRoutine";

    private ChangeRoutineViewModel vm;
    private RoutineRepository repo;
    private int dayId;
    private List<RoutineEntity> tasksList = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_routine;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate started");

        TextView dayName = findViewById(R.id.dayName);
        EditText tasksName = findViewById(R.id.nameTasksInput);
        Button saveButton = findViewById(R.id.addTaskButton);

        if (dayName == null || tasksName == null || saveButton == null) {
            Log.e(TAG, "Views not found!");
            Toast.makeText(this, "Error loading layout", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        vm = new ViewModelProvider(this).get(ChangeRoutineViewModel.class);
        repo = ((MyApplication) getApplication()).getRoutineRepository();

        dayId = getIntent().getIntExtra("day_id", -1);
        Log.d(TAG, "dayId: " + dayId);

        if (dayId < 0 || dayId >= 7) {
            Toast.makeText(this, "Invalid day", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String dayText = StaticMethods.getDay(dayId);
        dayName.setText(dayText != null ? dayText : "Unknown");

        vm.setHoursToSave(new HashSet<>());
        vm.setTextToChange("");

        repo.getTasksForDay(dayId).observe(this, tasks -> {
            Log.d(TAG, "Tasks received: " + (tasks != null ? tasks.size() : "null"));
            if (tasks != null) {
                tasksList = new ArrayList<>(tasks);
                vm.setTasks(tasks);
            } else {
                tasksList = new ArrayList<>();
                vm.setTasks(new ArrayList<>());
            }
        });

        vm.getTextToChange().observe(this, text -> {
            if (text != null && !text.isEmpty()) {
                tasksName.setText(text);
            }
        });

        if (savedInstanceState == null) {
            try {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.hoursFrame, new HoursFragment())
                        .commitNow();
                Log.d(TAG, "Fragment added");
            } catch (Exception e) {
                Log.e(TAG, "Error adding fragment", e);
                Toast.makeText(this, "Error loading hours", Toast.LENGTH_SHORT).show();
            }
        }

        saveButton.setOnClickListener(v -> {
            saveRoutine(tasksName.getText().toString());
        });

        Log.d(TAG, "onCreate finished");
    }

    private void saveRoutine(String name) {
        Set<Integer> hoursSet = vm.getHoursToSave().getValue();

        if (hoursSet == null || hoursSet.isEmpty()) {
            Toast.makeText(this, "Please select hours!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (name == null || name.trim().isEmpty()) {
            Toast.makeText(this, "Please enter task name!", Toast.LENGTH_SHORT).show();
            return;
        }

        name = name.trim();

        try {
            if (hoursSet.size() == 1) {
                int selectedHour = hoursSet.iterator().next();

                for (RoutineEntity item : tasksList) {
                    if (item.hour == selectedHour) {
                        repo.delete(item.id);
                    }
                }

                repo.insert(new RoutineEntity(name, dayId, selectedHour));
                Toast.makeText(this, "Task updated!", Toast.LENGTH_SHORT).show();

            } else {
                for (int hour : hoursSet) {
                    repo.insert(new RoutineEntity(name, dayId, hour));
                }
                Toast.makeText(this, hoursSet.size() + " tasks added!", Toast.LENGTH_SHORT).show();
            }

            finish();
        } catch (Exception e) {
            Log.e(TAG, "Error saving routine", e);
            Toast.makeText(this, "Error saving: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}