package com.example.calendar.DataBase.TasksTable;
import androidx.room.*;

@Entity(tableName = "tasks")
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "start_time")
    private String start;

    @ColumnInfo(name = "end_time")
    private String end;



    public TaskEntity(String title, String description, String date, String start, String end){
        this.title = title;
        this.description = description;
        this.date = date;
        this.start = start;
        this.end = end;
    }


    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public String getDate() {return date;}
    public String getStart() {return start;}
    public String getEnd() {return end;}


}


