package com.example.calendar.BasicTasks.AddTask;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calendar.R;

public class TimeFragment extends Fragment {
    private TimePicker timeStartPicker, timeEndPicker;
    private Button okBtn;
    private int hourStart, minuteStart, hourEnd, minuteEnd;

    SendTimeInterface sendTimeInterface;

    public interface SendTimeInterface {
        void sendTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);

        timeStartPicker = view.findViewById(R.id.timeStartPicker);
        timeStartPicker.setIs24HourView(true);
        timeEndPicker = view.findViewById(R.id.timeEndPicker);
        timeEndPicker.setIs24HourView(true);

        setTimePickerInterval(timeStartPicker);
        setTimePickerInterval(timeEndPicker);

        okBtn = view.findViewById(R.id.okButton);

        okBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                hourStart = timeStartPicker.getHour();
                minuteStart = getRealMinutes(timeStartPicker);
                hourEnd = timeEndPicker.getHour();
                minuteEnd = getRealMinutes(timeEndPicker);

                if ((hourEnd > hourStart) || (hourEnd == hourStart && minuteEnd > minuteStart)) {
                    sendTimeInterface.sendTime(hourStart, minuteStart, hourEnd, minuteEnd);
                    FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                    ft.remove(TimeFragment.this);
                    ft.commit();
                } else {
                    Toast.makeText(getContext(), "Chose correct end time", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void setTimePickerInterval(TimePicker timePicker) {
        try {
            NumberPicker minutePicker = timePicker.findViewById(
                    Resources.getSystem().getIdentifier("minute", "id", "android")
            );
            if (minutePicker != null) {
                minutePicker.setMinValue(0);
                minutePicker.setMaxValue(5);
                String[] displayedValues = {"00", "10", "20", "30", "40", "50"};
                minutePicker.setDisplayedValues(displayedValues);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getRealMinutes(TimePicker timePicker) {
        try {
            NumberPicker minutePicker = timePicker.findViewById(
                    Resources.getSystem().getIdentifier("minute", "id", "android")
            );
            if (minutePicker != null) {
                return minutePicker.getValue() * 10;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timePicker.getMinute();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        sendTimeInterface = (SendTimeInterface) activity;
    }
}
