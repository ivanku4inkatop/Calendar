package com.example.calendar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.calendar.TasksList.TaskItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DataBaseManager extends SQLiteOpenHelper {
    private static DataBaseManager dataBaseManager;
    private static final String DATABASE_NAME = "CalendarDataBase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Tasks";
    private static final String COUNTER = "Counter";

    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String DESC_FIELD = "desc";
    private static final String DELETED_FIELD = "deleted";
    private static final String DATE_FIELD = "date";
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public DataBaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBaseManager instanceOfDataBase(Context context){
        if(dataBaseManager == null){
            dataBaseManager = new DataBaseManager(context);
        }
        return dataBaseManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuffer sql;
        sql = new StringBuffer()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(TITLE_FIELD)
                .append(" TEXT, ")
                .append(DESC_FIELD)
                .append(" TEXT, ")
                .append(DATE_FIELD)
                .append(" TEXT)");

        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void addTask(TaskItem taskItem){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, taskItem.getId());
        contentValues.put(TITLE_FIELD, taskItem.getName());
        contentValues.put(DESC_FIELD, taskItem.getDescription());
        contentValues.put(DATE_FIELD, getStringFromDate(taskItem.getDate()));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void populateTaskListArray(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        TaskItem.getTasksArray().clear();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if(result.getCount() != 0){
                while(result.moveToNext()){
                    int id = result.getInt(1);
                    String name = result.getString(2);
                    String description = result.getString(3);
                    String dateString = result.getString(4);
                    Calendar date = getDateFromString(dateString);
                    TaskItem taskItem = new TaskItem(id, name, description, date);
                    TaskItem.getTasksArray().add(taskItem);
                }
            }
        }
    }

    public void updateTaskInDB(TaskItem taskItem){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_FIELD, taskItem.getId());
        contentValues.put(TITLE_FIELD, taskItem.getName());
        contentValues.put(DESC_FIELD, taskItem.getDescription());
        contentValues.put(DATE_FIELD, getStringFromDate(taskItem.getDate()));

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " = ?", new String[]{String.valueOf(taskItem.getId())});

    }

    public void deleteTaskFromDB(int taskId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, ID_FIELD + " = ?", new String[]{String.valueOf(taskId)});
    }


    private String getStringFromDate(Calendar date) {
        if(date == null){
            return null;
        }
        return dateFormat.format(date.getTime());
    }

    private Calendar getDateFromString(String date){
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

}
