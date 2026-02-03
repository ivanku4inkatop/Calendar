package com.example.calendar.DataBase.RoutineTable;

import androidx.room.ColumnInfo;

public class DayCount {
    @ColumnInfo(name = "dayOfWeek")
    public int day;

    @ColumnInfo(name = "count")
    public int count;

    public DayCount(int day, int count){
        this.day = day;
        this.count = count;
    }

    public int getDay(){return day;}
    public int getCount(){return count;}
}
