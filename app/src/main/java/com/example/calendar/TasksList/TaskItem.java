package com.example.calendar.TasksList;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskItem {
    private static ArrayList<TaskItem> tasksArray = new ArrayList<>();
    private int id;
    private String name;
    private String description;
    private Calendar date;


    public TaskItem(int id, String name, String description, Calendar date){
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public Calendar getDate() {return date;}
    public void setDate(Calendar date) {this.date = date;}
    public static ArrayList<TaskItem> getTasksArray() {return tasksArray;}
    public void setTasksArray(ArrayList<TaskItem> tasksArray) {this.tasksArray = tasksArray;}
}
