package com.example.calendar.DataBase.TasksTable;
import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;


@Dao
public interface TasksDao {

    @Insert
    void insert(TaskEntity task);

    @Update
    void update(TaskEntity task);

    @Delete
    void delete(TaskEntity task);

    @Query("SELECT * FROM tasks")
    LiveData<List<TaskEntity>> getAll();

    @Query("SELECT * FROM tasks WHERE id = :id")
    LiveData<TaskEntity> getById(int id);
}
