package com.example.calendar.RoutineTasks;

import android.content.Context;
import android.content.Intent;
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

public class DaysAdapter extends ArrayAdapter<Integer> {

    public DaysAdapter(Context context, List<Integer> counts){
        super(context, 0, counts);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String day = StaticMethods.getDay(position);
        int tasksNumber = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.day_item, parent, false);
        }

        TextView nameItem = convertView.findViewById(R.id.nameItem);
        TextView countItem = convertView.findViewById(R.id.countItem);

        nameItem.setText(day);
        countItem.setText("Tasks: " + tasksNumber);

        nameItem.setOnClickListener(v -> {
            Context context = getContext();
            Intent intent = new Intent(context, ChangeRoutine.class);
            intent.putExtra("day_id", position);
            context.startActivity(intent);
        });


        return convertView;
    }

}
