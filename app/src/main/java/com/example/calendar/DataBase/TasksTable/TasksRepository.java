package com.example.calendar.DataBase.TasksTable;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.calendar.DataBase.AppDatabase;
import com.example.calendar.DataBase.DatabaseExecutor;

import java.util.List;

public class TasksRepository {
    private final TasksDao dao;

    public TasksRepository(Context context) {
        dao = AppDatabase.getInstance(context).tasksDao();
    }

    public void insert(TaskEntity task) {
        DatabaseExecutor.executor.execute(() -> dao.insert(task));
    }

    public void update(TaskEntity task) {
        DatabaseExecutor.executor.execute(() -> dao.update(task));
    }

    public void delete(TaskEntity task) {
        DatabaseExecutor.executor.execute(() -> dao.delete(task));
    }

    public LiveData<List<TaskEntity>> getAll() {
        return dao.getAll();
    }

    public LiveData<TaskEntity> getById(int id) {
        return dao.getById(id);
    }
}
