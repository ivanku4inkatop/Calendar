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
import android.widget.TextView;

import com.example.calendar.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;


public class DateCalendarFragment extends Fragment {
    private Button closeBtn, nextBtn, previousBtn;
    private TextView monthTxt;
    private int monthCount = 0;
    Calendar dateTask;
    SendDateInterface sendDateInterface;

    public interface SendDateInterface {
        public void SendDate(Calendar calendar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_calendar, container, false);

        monthTxt = view.findViewById(R.id.month);
        closeBtn = view.findViewById(R.id.close);
        nextBtn = view.findViewById(R.id.nextMonth);
        previousBtn = view.findViewById(R.id.previousMonth);

        generateCalendar(view);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.remove(DateCalendarFragment.this);
                ft.commit();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1){
                monthCount++;
                generateCalendar(view);
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1){
                if(monthCount >= 1){
                    monthCount--;
                    generateCalendar(view);
                }
            }
        });


        return view;
    }

    private void generateCalendar(View view){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, monthCount);
        int monthIndex = calendar.get(Calendar.MONTH);
        String monthName = new DateFormatSymbols(Locale.ENGLISH).getMonths()[monthIndex];

        monthTxt.setText(monthName);

        if(monthCount == 0){
            int dayCalendar = calendar.get(Calendar.DAY_OF_MONTH);
            int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            int dayFirst = calendar.get(Calendar.DAY_OF_WEEK);
            fillCalendar(dayCalendar, dayFirst + dayWeek, view);

        }
        else{
            calendar.set(Calendar.DAY_OF_MONTH, 0);
            int dayFirst = calendar.get(Calendar.DAY_OF_WEEK);
            fillCalendar(1, dayFirst, view);
        }
    }

    private void fillCalendar(int day, int dayWeek, View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, monthCount);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        int month = calendar.get(Calendar.MONTH);
        int totalCells = 42;
        int currentCell = 1;

        for (; currentCell < dayWeek; currentCell++) {
            setDayButton(view, currentCell, "", null);
        }

        for (; day <= 31 && currentCell <= totalCells; day++, currentCell++) {
            if (calendar.get(Calendar.MONTH) != month) break;

            Calendar selectedDate = (Calendar) calendar.clone();
            setDayButton(view, currentCell, String.valueOf(day), v -> {
                if (sendDateInterface != null) {
                    sendDateInterface.SendDate(selectedDate);
                }
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.remove(DateCalendarFragment.this);
                ft.commit();
            });

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        for (; currentCell <= totalCells; currentCell++) {
            setDayButton(view, currentCell, "", null);
        }
    }
    private void setDayButton(View view, int cellIndex, String text, View.OnClickListener listener) {
        int resID = getResources().getIdentifier("day" + cellIndex, "id", getContext().getPackageName());
        TextView dayBtn = view.findViewById(resID);
        if (dayBtn != null) {
            dayBtn.setText(text);
            dayBtn.setOnClickListener(listener != null ? listener : v -> {});
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        sendDateInterface =(SendDateInterface) activity;
    }
}