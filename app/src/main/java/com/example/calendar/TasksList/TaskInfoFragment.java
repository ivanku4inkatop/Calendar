package com.example.calendar.TasksList;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.calendar.AddTask.AddTask;
import com.example.calendar.DataBase.TasksTable.TaskEntity;
import com.example.calendar.R;


public class TaskInfoFragment extends Fragment {

    TextView nameText, descriptionText, dateText;
    Button closeBtn, editBtn;
    TaskEntity taskEntity;

    public TaskInfoFragment(TaskEntity taskEntity){
        this.taskEntity = taskEntity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_info, container, false);
        nameText = view.findViewById(R.id.textName);
        descriptionText = view.findViewById(R.id.textDescription);
        dateText = view.findViewById(R.id.textDate);
        closeBtn = view.findViewById(R.id.closeInfo);
        editBtn = view.findViewById(R.id.buttonEdit);

        nameText.setText(taskEntity.getTitle());
        descriptionText.setText(taskEntity.getDescription());
        dateText.setText(taskEntity.getDate());

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.remove(TaskInfoFragment.this);
                ft.commit();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                Intent intent = new Intent(activity, AddTask.class);
                intent.putExtra("edit_task_id", taskEntity.getId());
                activity.startActivity(intent);
            }
        });

        return view;
    }
}