package com.example.calendar;

import java.util.Calendar;

public class TasksController {
    private TodayData todayData;
    private Calendar calendar;
    public TasksController(TodayData todayData){
        this.todayData = todayData;
    }
    public void checkDate(){
        calendar = Calendar.getInstance();

        if(!isSameDay(calendar, todayData.getTodayDate())){
            todayData.setTodayDate(calendar);
            getTodayTasks();
        }
    }

    private void getTodayTasks(){

    }

    private static boolean isSameDay(Calendar c1, Calendar c2) {
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

}
