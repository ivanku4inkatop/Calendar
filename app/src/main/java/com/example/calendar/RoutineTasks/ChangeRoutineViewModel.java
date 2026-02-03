package com.example.calendar.RoutineTasks;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.calendar.DataBase.RoutineTable.RoutineEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChangeRoutineViewModel extends ViewModel {
    private final MutableLiveData<List<RoutineEntity>> tasks = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> textToChange = new MutableLiveData<>();
    private final MutableLiveData<Set<Integer>> hoursToSave = new MutableLiveData<>(new HashSet<>());

    public LiveData<List<RoutineEntity>> getTasks(){return tasks;}
    public LiveData<String> getTextToChange(){return textToChange;}
    public LiveData<Set<Integer>> getHoursToSave(){return hoursToSave;}

    public void setTasks(List<RoutineEntity> list){tasks.setValue(list);}
    public void setTextToChange(String text){textToChange.setValue(text);}
    public void setHoursToSave(Set<Integer> hours){hoursToSave.setValue(hours);}
}
