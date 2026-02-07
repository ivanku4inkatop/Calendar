package com.example.calendar.TodayTasks;

import android.util.EventLogTags;

import java.util.Calendar;

public class Task {
    private String title;
    private String description;
    private Calendar timeStart;
    private Calendar timeEnd;
    private boolean routine;
    public Task(String title, String description, Calendar timeStart, Calendar timeEnd, boolean routine) {
        this.title = title;
        this.description = description;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.routine = routine;
    }

    public Calendar getTimeStart() {return timeStart;}
    public void setTimeStart(Calendar timeStart) {this.timeStart = timeStart;}
    public Calendar getTimeEnd() {return timeEnd;}
    public void setTimeEnd(Calendar timeEnd) {this.timeEnd = timeEnd;}
    public boolean isRoutine() {return routine;}
    public void setRoutine(boolean routine) {this.routine = routine;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
}
