package com.example.calendar;

import java.util.Calendar;

public class TodayData {
    private Calendar todayDate;
    private Calendar timeNow;
    public Calendar getTimeNow() {return timeNow;}
    public void setTimeNow(Calendar timeNow) {this.timeNow = timeNow;}
    public Calendar getTodayDate() {return todayDate;}
    public void setTodayDate(Calendar todayDate) {this.todayDate = todayDate;}
}
