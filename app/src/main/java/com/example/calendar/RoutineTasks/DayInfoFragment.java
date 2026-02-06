package com.example.calendar.RoutineTasks;

import static androidx.lifecycle.AndroidViewModel_androidKt.getApplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.calendar.DataBase.RoutineTable.RoutineEntity;
import com.example.calendar.DataBase.RoutineTable.RoutineRepository;
import com.example.calendar.MyApplication;
import com.example.calendar.R;
import com.example.calendar.RoutineTasks.EditRoutine.ChangeRoutine;
import com.example.calendar.Templates.FragmentInfoTemplate;
import com.example.calendar.Templates.StaticMethods;

public class DayInfoFragment extends FragmentInfoTemplate {

    private static final String ARG_NUMBER = "arg_number";
    private int number;
    private RoutineRepository repo;

    LinearLayout schedule;

    public static DayInfoFragment newInstance(int number) {
        DayInfoFragment fragment = new DayInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NUMBER, number);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            number = getArguments().getInt(ARG_NUMBER);
        }
        setTitleText(StaticMethods.getDay(number));
        View content = LayoutInflater.from(getContext()).inflate(R.layout.fragment_day_info, getWrap(), true);
        schedule = view.findViewById(R.id.scheduleLayout);
        repo = ((MyApplication) requireActivity().getApplication()).getRoutineRepository();

        addToSchedule();

    }


    @Override
    protected void setEditButton(){
        Context context = getContext();
        Intent intent = new Intent(context, ChangeRoutine.class);
        intent.putExtra("day_id", number);
        context.startActivity(intent);
    }

    private void addToSchedule(){
        schedule.removeAllViews();
        repo.getTasksForDay(number).observe(getViewLifecycleOwner(), tasks -> {
            schedule.removeAllViews();

            if(tasks == null || tasks.isEmpty()){
                View tasksLine = LayoutInflater.from(getContext())
                        .inflate(R.layout.empty_slot, schedule, false);
                TextView name = tasksLine.findViewById(R.id.nameEmptyItem);
                name.setText("No Tasks Yet");
                name.setTextSize(36);

                schedule.addView(tasksLine);
            }
            else {
                for (int i = 0; i < 24; i++) {
                    View tasksLine;
                    boolean found = false;

                    for(RoutineEntity item : tasks){
                        if (item.hour == i){
                            tasksLine = LayoutInflater.from(getContext())
                                    .inflate(R.layout.task_day_slot, schedule, false);

                            TextView name = tasksLine.findViewById(R.id.nameSlotItem);
                            TextView time = tasksLine.findViewById(R.id.timeSlotItem);

                            if (name != null) {
                                name.setText(item.title);
                            }
                            if (time != null) {
                                time.setText(item.hour + ":00");
                            }

                            schedule.addView(tasksLine);
                            found = true;
                            break;
                        }
                    }

                    if (!found){
                        tasksLine = LayoutInflater.from(getContext())
                                .inflate(R.layout.empty_slot, schedule, false);
                        schedule.addView(tasksLine);
                    }
                }
            }
        });
    }
}