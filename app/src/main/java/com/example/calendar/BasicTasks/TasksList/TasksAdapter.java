package com.example.calendar.BasicTasks.TasksList;

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

import com.example.calendar.DataBase.TasksTable.TaskEntity;
import com.example.calendar.DataBase.TasksTable.TasksRepository;
import com.example.calendar.MyApplication;
import com.example.calendar.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class TasksAdapter extends ArrayAdapter<TaskEntity> {
    public TasksAdapter(Context context, List<TaskEntity> tasks) {
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TaskEntity taskEntity = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }
        TextView nameItem = convertView.findViewById(R.id.nameItem);
        TextView dateItem = convertView.findViewById(R.id.dateItem);
        ImageButton deleteBtn = convertView.findViewById(R.id.deleteButton);


        if (taskEntity.getDate() != null) {
            dateItem.setText(taskEntity.getDate());
        } else {
            dateItem.setText("-");
        }

        nameItem.setText(taskEntity.getTitle());
        nameItem.setOnClickListener(v -> {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                TaskInfoFragment taskInfoFragment = new TaskInfoFragment(taskEntity);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.infoLayout, taskInfoFragment)
                        .addToBackStack(null)
                        .commit();

        });

        deleteBtn.setOnClickListener(v ->{
                deleteTask(taskEntity);
            });
        return convertView;
    }

    private void deleteTask(TaskEntity taskEntity){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete")
                .setMessage("Are you want to delete task?")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyApplication app = (MyApplication) getContext().getApplicationContext();
                        TasksRepository repo = app.getTasksRepository();
                        repo.delete(taskEntity);

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
