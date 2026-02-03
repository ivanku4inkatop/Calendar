package com.example.calendar.DataBase.RoutineTable;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.calendar.DataBase.AppDatabase;
import com.example.calendar.DataBase.DatabaseExecutor;

import java.util.List;

public class RoutineRepository {
    private final RoutineDao dao;

    public RoutineRepository(Context context){
        dao = AppDatabase.getInstance(context).routineDao();
    }

    public void insert(RoutineEntity routine){
        DatabaseExecutor.executor.execute(() -> dao.insert(routine));
    }

    public void delete(int id){
        DatabaseExecutor.executor.execute(() -> dao.delete(id));
    }

    public LiveData<List<RoutineEntity>> getTasksForDay(int day){
        return dao.getTasksForDay(day);
    }

    public LiveData<List<DayCount>> getTasksCountPerDay(){
        return dao.getTasksCountPerDay();
    }
}
