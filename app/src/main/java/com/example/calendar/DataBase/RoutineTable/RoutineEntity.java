package com.example.calendar.DataBase.RoutineTable;

import androidx.room.*;

@Entity(tableName = "routine")
public class RoutineEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "dayOfWeek")
    public int dayOfWeek;

    @ColumnInfo(name = "hour")
    public int hour;

    public RoutineEntity(String title, int dayOfWeek, int hour) {
        this.title = title;
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
    }

    public int getId() {return id;}
    public String getTitle() {return title;}
    public int getDayOfWeek() {return dayOfWeek;}
    public int getHour() {return hour;}
}
