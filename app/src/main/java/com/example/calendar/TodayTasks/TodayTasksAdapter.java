package com.example.calendar.TodayTasks;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.calendar.R;
import com.example.calendar.Templates.StaticMethods;

import java.util.List;

public class TodayTasksAdapter extends ArrayAdapter<Task> {

    public TodayTasksAdapter(Context context, List<Task> tasks){
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_day_slot, parent, false);
        }
        TextView time = convertView.findViewById(R.id.timeSlotItem);
        TextView title = convertView.findViewById(R.id.nameSlotItem);
        Task task = getItem(position);

        title.setText(task.getTitle());
        time.setText(StaticMethods.getStringFromTime(task.getTimeStart()));
        if(task.isRoutine()){
            title.setBackgroundColor(Color.LTGRAY);
            time.setBackgroundColor(Color.LTGRAY);
        }
        else{
            title.setBackgroundColor(Color.BLUE);
            time.setBackgroundColor(Color.BLUE);
        }
        convertView.setOnClickListener(v -> {

        });


        return convertView;
    }
}
