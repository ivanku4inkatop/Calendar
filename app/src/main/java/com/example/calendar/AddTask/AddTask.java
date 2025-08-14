package com.example.calendar.AddTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.calendar.DataBaseManager;
import com.example.calendar.MainActivity;
import com.example.calendar.R;
import com.example.calendar.TasksList.TaskItem;
import com.example.calendar.TasksList.TasksList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTask extends AppCompatActivity implements DateCalendarFragment.SendDateInterface, TimeFragment.SendTimeInterface {

    private Button addTaskBtn, choseDateBtn, backBtn;
    private EditText nameInp, descriptionInp;
    private TextView timeTxt;
    private Switch timeSwitch;
    private String nameTask, descriptionTask;
    private int id;
    private Calendar dateTask = null, timeStart = null, timeEnd = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        nameInp = findViewById(R.id.tasksName);
        choseDateBtn = findViewById(R.id.choseDate);
        addTaskBtn = findViewById(R.id.addTask);
        timeSwitch = findViewById(R.id.isTime);
        timeTxt = findViewById(R.id.timeSet);
        descriptionInp = findViewById(R.id.descriptionTask);
        backBtn = findViewById(R.id.back);
        TimeFragment timeFragment = new TimeFragment();

        choseDateBtn.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view){
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                DateCalendarFragment dateCalendar = new DateCalendarFragment();
                ft.replace(R.id.calendarLayout, dateCalendar);
                ft.commit();
            }
        });

        timeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (isChecked) {
                    TimeFragment timeFragment = new TimeFragment();
                    ft.replace(R.id.timeLayout, timeFragment);
                } else {
                    Fragment current = getSupportFragmentManager().findFragmentById(R.id.timeLayout);
                    timeEnd = null;
                    timeStart = null;
                    timeTxt.setText("");
                    if (current != null) {
                        ft.remove(current);
                    }
                }


                ft.commit();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTask.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseManager dataBaseManager = DataBaseManager.instanceOfDataBase(AddTask.this);
                nameTask = nameInp.getText().toString();
                descriptionTask = descriptionInp.getText().toString();
                id = TaskItem.getTasksArray().size();

                TaskItem taskItem = new TaskItem(id, nameTask, descriptionTask, dateTask);
                TaskItem.getTasksArray().add(taskItem);
                dataBaseManager.addTask(taskItem);

                Intent intent = new Intent(AddTask.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public void SendDate(Calendar calendar) {
        dateTask = calendar;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String formattedDate = sdf.format(dateTask.getTime());

        choseDateBtn.setText(formattedDate);
    }

    @Override
    public void sendTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd) {
        timeStart = Calendar.getInstance();
        timeStart.set(Calendar.HOUR_OF_DAY, hourStart);
        timeStart.set(Calendar.MINUTE, minuteStart);

        timeEnd = Calendar.getInstance();
        timeEnd.set(Calendar.HOUR_OF_DAY, hourEnd);
        timeEnd.set(Calendar.MINUTE, minuteEnd);
        timeTxt.setText(String.format("%02d:%02d - %02d:%02d", hourStart, minuteStart, hourEnd, minuteEnd));
    }
}