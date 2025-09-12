package com.example.calendar;

import com.example.calendar.TasksList.TaskItem;

import java.util.ArrayList;
import java.util.Calendar;

public class TodayData {
    private Calendar todayDate;
    private Calendar timeNow;
    private ArrayList<TaskItem> tasksArray;
    private ArrayList<TaskItem> eventsArray;


    public ArrayList<TaskItem> getEventsArray() {return eventsArray;}
    public void setEventsArray(ArrayList<TaskItem> eventsArray) {this.eventsArray = eventsArray;}
    public ArrayList<TaskItem> getTasksArray() {return tasksArray;}
    public void setTasksArray(ArrayList<TaskItem> tasksArray) {this.tasksArray = tasksArray;}
    public Calendar getTimeNow() {return timeNow;}
    public void setTimeNow(Calendar timeNow) {this.timeNow = timeNow;}
    public Calendar getTodayDate() {return todayDate;}
    public void setTodayDate(Calendar todayDate) {this.todayDate = todayDate;}
}
