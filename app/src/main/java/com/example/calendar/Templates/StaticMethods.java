package com.example.calendar.Templates;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StaticMethods {
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm");

    private static final List<String> days = List.of(
            "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "Sunday");

    public static String getStringFromDate(Calendar date) {
        if(date == null){
            return null;
        }
        return dateFormat.format(date.getTime());
    }

    public static Calendar getDateFromString(String date){
        if (date == null || date.isEmpty()) {
            return null;
        }
        try {
            Date parsedDate = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            return calendar;
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getStringFromTime(Calendar time){
        if(time == null){
            return null;
        }
        return timeFormat.format(time.getTime());
    }

    public static Calendar getTimeFromString(String time){
        if(time == null || time.isEmpty()){
            return null;
        }
        try{
            Date parsedTime = timeFormat.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedTime);
            return calendar;
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDay(int num){
        if (num >= 0 && num < days.size()) {
            return days.get(num);
        }
        return "Unknown";
    }
}
