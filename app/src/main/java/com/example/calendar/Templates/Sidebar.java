package com.example.calendar.Templates;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.calendar.R;
import com.example.calendar.BasicTasks.TasksList.TasksList;
import com.example.calendar.RoutineTasks.RoutineTasks;

public class Sidebar extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sidebar, container, false);

        TextView tasksButton = view.findViewById(R.id.tasksButton);
        TextView routineButton = view.findViewById(R.id.routineButton);


        tasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), TasksList.class);
                startActivity(intent);
            }
        });

        routineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), RoutineTasks.class);
                startActivity(intent);
            }
        });

        return view;
    }
}