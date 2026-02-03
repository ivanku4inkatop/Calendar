package com.example.calendar.DataBase.RoutineTable;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoutineDao {
    @Insert
    void insert(RoutineEntity task);

    @Query("SELECT * FROM routine WHERE dayOfWeek = :day ORDER BY hour")
    LiveData<List<RoutineEntity>> getTasksForDay(int day);

    @Query("DELETE FROM routine WHERE id = :id")
    void delete(int id);

    @Query("SELECT dayOfWeek, COUNT(*) as count FROM routine GROUP BY dayOfWeek")
    LiveData<List<DayCount>> getTasksCountPerDay();
}
