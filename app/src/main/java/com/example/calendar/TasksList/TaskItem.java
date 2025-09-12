package com.example.calendar.TasksList;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskItem implements Parcelable {
    private static ArrayList<TaskItem> tasksArray = new ArrayList<>();
    private int id;
    private String name;
    private String description;
    private Calendar date, timeStart, timeEnd;

    public TaskItem(int id, String name, String description, Calendar date, Calendar timeStart, Calendar timeEnd){
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    protected TaskItem(Parcel in){
        id = in.readInt();
        name = in.readString();
        description = in.readString();

        long dateMillis = in.readLong();
        if (dateMillis != -1) {
            date = Calendar.getInstance();
            date.setTimeInMillis(dateMillis);
        }

        long startMillis = in.readLong();
        if (startMillis != -1) {
            timeStart = Calendar.getInstance();
            timeStart.setTimeInMillis(startMillis);
        }

        long endMillis = in.readLong();
        if (endMillis != -1) {
            timeEnd = Calendar.getInstance();
            timeEnd.setTimeInMillis(endMillis);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);

        dest.writeLong(date != null ? date.getTimeInMillis() : -1);
        dest.writeLong(timeStart != null ? timeStart.getTimeInMillis() : -1);
        dest.writeLong(timeEnd != null ? timeEnd.getTimeInMillis() : -1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<TaskItem> CREATOR = new Creator<TaskItem>() {
        @Override
        public TaskItem createFromParcel(Parcel in) {
            return new TaskItem(in);
        }

        @Override
        public TaskItem[] newArray(int size) {
            return new TaskItem[size];
        }
    };

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
    public Calendar getTimeEnd() {return timeEnd;}
    public void setTimeEnd(Calendar timeEnd) {this.timeEnd = timeEnd;}
    public Calendar getTimeStart() {return timeStart;}
    public void setTimeStart(Calendar timeStart) {this.timeStart = timeStart;}
}
