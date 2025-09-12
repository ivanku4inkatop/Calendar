package com.example.calendar.TasksList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.calendar.AddTask.AddTask;
import com.example.calendar.MainActivity;
import com.example.calendar.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TaskInfoFragment extends Fragment {

    TextView nameText, descriptionText, dateText;
    Button closeBtn, editBtn;
    TaskItem taskItem;

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public TaskInfoFragment(TaskItem taskItem){
        this.taskItem = taskItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_info, container, false);
        nameText = view.findViewById(R.id.textName);
        descriptionText = view.findViewById(R.id.texDescription);
        dateText = view.findViewById(R.id.texDate);
        closeBtn = view.findViewById(R.id.closeInfo);
        editBtn = view.findViewById(R.id.buttonEdit);

        nameText.setText(taskItem.getName());
        descriptionText.setText(taskItem.getDescription());
        dateText.setText(convertDate(taskItem.getDate()));

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
                intent.putExtra("edit_task", (Parcelable) taskItem);
                activity.startActivity(intent);
            }
        });

        return view;
    }

    private String convertDate(Calendar calendar){
        if(calendar == null){
            return null;
        }
        return dateFormat.format(calendar.getTime());
    }
}