package com.example.calendar.RoutineTasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.calendar.DataBase.RoutineTable.DayCount;
import com.example.calendar.DataBase.RoutineTable.RoutineRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoutineTasksViewModel extends AndroidViewModel {

    private final RoutineRepository repo;

    public RoutineTasksViewModel(@NonNull Application application) {
        super(application);
        repo = new RoutineRepository(application);
    }

    public LiveData<List<Integer>> tasksCountPerWeek() {
        return Transformations.map(
                repo.getTasksCountPerDay(),
                list -> {
                    List<Integer> result = new ArrayList<>(Collections.nCopies(7, 0));
                    if (list != null && !list.isEmpty()) {
                        for (DayCount dc : list) {
                            result.set(dc.getDay(), dc.getCount());
                        }
                    }
                    return result;
                }
        );
    }
}

