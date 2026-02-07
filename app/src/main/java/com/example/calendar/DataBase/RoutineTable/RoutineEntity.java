package com.example.calendar.DataBase.RoutineTable;

import androidx.room.*;

@Entity(tableName = "routine")
public class RoutineEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "dayOfWeek")
    private int dayOfWeek;

    @ColumnInfo(name = "hour")
    private int hour;

    public RoutineEntity(String title, int dayOfWeek, int hour) {
        this.title = title;
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
    }

    public int getId() {return id;}
    public String getTitle() {return title;}
    public int getDayOfWeek() {return dayOfWeek;}
    public int getHour() {return hour;}

    public void setId(int id) {this.id = id;}
    public void setTitle(String title) {this.title = title;}
    public void setDayOfWeek(int dayOfWeek) {this.dayOfWeek = dayOfWeek;}
    public void setHour(int hour) {this.hour = hour;}
}