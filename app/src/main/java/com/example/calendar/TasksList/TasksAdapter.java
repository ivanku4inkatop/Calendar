package com.example.calendar.TasksList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calendar.DataBaseManager;
import com.example.calendar.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class TasksAdapter extends ArrayAdapter<TaskItem> {
    public TasksAdapter(Context context, List<TaskItem> tasks) {
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TaskItem taskItem = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }
        TextView nameItem = convertView.findViewById(R.id.nameItem);
        TextView dateItem = convertView.findViewById(R.id.dateItem);
        ImageButton deleteBtn = convertView.findViewById(R.id.deleteButton);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM");

        if (taskItem.getDate() != null) {
            dateItem.setText(dateFormat.format(taskItem.getDate().getTime()));
        } else {
            dateItem.setText("-");
        }

        nameItem.setText(taskItem.getName());
        nameItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                TaskInfoFragment taskInfoFragment = new TaskInfoFragment(taskItem);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.infoLayout, taskInfoFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTask(taskItem);
            }
        });
        return convertView;
    }


    private void showInfo(){

    }

    private void deleteTask(TaskItem taskItem){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete")
                .setMessage("Are you want to delete task?")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataBaseManager dataBaseManager = DataBaseManager.instanceOfDataBase(getContext());
                        dataBaseManager.deleteTaskFromDB(taskItem.getId());

                        TaskItem.getTasksArray().remove(taskItem);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
