package com.example.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.calendar.DataBase.RoutineTable.RoutineEntity;
import com.example.calendar.RoutineTasks.ChangeRoutineViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HoursFragment extends Fragment {

    private final Set<Integer> selectedHours = new HashSet<>();
    private List<RoutineEntity> tasksList = new ArrayList<>();
    private ChangeRoutineViewModel vm;
    private int busyHour = -1;
    private Map<Integer, String> tasksMap = new LinkedHashMap<>();
    private GridLayout grid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hours, container, false);
        grid = view.findViewById(R.id.gridHours);

        if (grid == null) {
            return view;
        }

        vm = new ViewModelProvider(requireActivity()).get(ChangeRoutineViewModel.class);

        vm.getTasks().observe(getViewLifecycleOwner(), tasks -> {

            if (tasks != null) {
                tasksList = new ArrayList<>(tasks);

                tasksMap.clear();
                for (RoutineEntity task : tasksList) {
                    tasksMap.put(task.getHour(), task.getTitle());
                }

                createHourButtons();
            }
        });

        return view;
    }

    private void createHourButtons() {
        grid.removeAllViews();

        for (int hour = 0; hour < 24; hour++) {
            Button btn = new Button(getContext());
            btn.setText(String.valueOf(hour));
            btn.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            if (tasksMap.containsKey(hour)) {
                btn.setBackgroundColor(Color.GREEN);
                setupBusyHourButton(btn, hour, tasksMap.get(hour));
            } else {
                btn.setBackgroundColor(Color.DKGRAY);
                setupFreeHourButton(btn, hour);
            }

            grid.addView(btn);
        }

    }

    private void setupBusyHourButton(Button btn, int hour, String taskTitle) {
        btn.setOnClickListener(v -> {
            if (selectedHours.isEmpty()) {
                if (busyHour == -1) {
                    busyHour = hour;
                    btn.setBackgroundColor(Color.BLUE);
                    vm.setTextToChange(taskTitle);
                    selectedHours.add(hour);
                    vm.setHoursToSave(selectedHours);
                } else {
                    if (busyHour == hour) {
                        busyHour = -1;
                        btn.setBackgroundColor(Color.GREEN);
                        vm.setTextToChange("");
                        selectedHours.remove(hour);
                        vm.setHoursToSave(selectedHours);
                    } else {
                        Toast.makeText(getContext(),
                                "You can't select this item!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getContext(),
                        "You can't select this item!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupFreeHourButton(Button btn, int hour) {
        btn.setOnClickListener(v -> {
            if (busyHour != -1) {
                Toast.makeText(getContext(),
                        "You can't select this item!",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (selectedHours.contains(hour)) {
                    selectedHours.remove(hour);
                    btn.setBackgroundColor(Color.DKGRAY);
                } else {
                    selectedHours.add(hour);
                    btn.setBackgroundColor(Color.BLUE);
                }
                vm.setHoursToSave(selectedHours);
            }
        });
    }

}