package com.example.calendar.AddTask;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.calendar.R;

public class TimeFragment extends Fragment {
    private TimePicker timeStartPicker, timeEndPicker;
    private Button okBtn;
    private int hourStart, minuteStart, hourEnd, minuteEnd;
    SendTimeInterface sendTimeInterface;
    public interface SendTimeInterface {
        public void sendTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);

        timeStartPicker = view.findViewById(R.id.timeStartPicker);
        timeStartPicker.setIs24HourView(true);
        timeEndPicker = view.findViewById(R.id.timeEndPicker);
        timeEndPicker.setIs24HourView(true);
        okBtn = view.findViewById(R.id.okButton);

        okBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                hourStart = timeStartPicker.getHour();
                minuteStart = timeStartPicker.getMinute();
                hourEnd = timeEndPicker.getHour();
                minuteEnd = timeEndPicker.getMinute();
                if((hourEnd > hourStart) || (hourEnd == hourStart && minuteEnd > minuteStart)){
                    sendTimeInterface.sendTime(hourStart, minuteStart, hourEnd, minuteEnd);
                    FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                    ft.remove(TimeFragment.this);
                    ft.commit();
                }
                else{
                    Toast.makeText(getContext(), "Chose correct end time", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        sendTimeInterface = (SendTimeInterface) activity;
    }
}