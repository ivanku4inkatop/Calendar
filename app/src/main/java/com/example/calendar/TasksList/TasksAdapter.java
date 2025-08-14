package com.example.calendar.TasksList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseManager dataBaseManager = DataBaseManager.instanceOfDataBase(getContext());
                dataBaseManager.deleteTaskFromDB(taskItem.getId());

                TaskItem.getTasksArray().remove(taskItem);
                notifyDataSetChanged();
            }
        });


        return convertView;
    }
}
