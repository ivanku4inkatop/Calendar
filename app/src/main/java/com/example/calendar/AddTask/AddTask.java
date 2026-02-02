package com.example.calendar.AddTask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.calendar.DataBase.CalendarTranslator;
import com.example.calendar.DataBase.TasksTable.TaskEntity;
import com.example.calendar.DataBase.TasksTable.TasksRepository;
import com.example.calendar.MainActivity;
import com.example.calendar.MyApplication;
import com.example.calendar.R;

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
    TaskEntity deleteItem = null;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());




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

        TasksRepository repo = ((MyApplication) getApplication()).getTasksRepository();

        int taskId = getIntent().getIntExtra("edit_task_id", -1);
        if (taskId != -1) {
            repo.getById(taskId).observe(this, task -> {
                if (task == null) return;

                deleteItem = task;

                nameInp.setText(task.getTitle());
                descriptionInp.setText(task.getDescription());

                dateTask = CalendarTranslator.getDateFromString(task.getDate());
                choseDateBtn.setText(task.getDate());

                if (task.getStart() != null && task.getEnd() != null) {
                    timeSwitch.setChecked(true);

                    String start = task.getStart();
                    String end = task.getEnd();
                    timeTxt.setText(start + " - " + end);

                    timeTxt.setOnClickListener(v -> timeButtonListener());
                }
            });
        }


        choseDateBtn.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view){
                openDateMenu();
            }
        });

        timeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TimeFragment timeFragment = new TimeFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.timeLayout, timeFragment)
                            .commit();
                    timeTxt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            timeButtonListener();
                        }
                    });
                } else {
                    Fragment current = getSupportFragmentManager().findFragmentById(R.id.timeLayout);
                    timeEnd = null;
                    timeStart = null;
                    timeTxt.setText("");
                    timeTxt.setOnClickListener(null);
                    if (current != null) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .remove(current)
                                .commit();
                    }
                }
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
                taskChecker();
            }
        });
    }

    private void taskChecker(){
        nameTask = nameInp.getText().toString();
        descriptionTask = descriptionInp.getText().toString();
        if(nameTask.trim().isEmpty()){
            Toast.makeText(AddTask.this, "Write task's name!", Toast.LENGTH_SHORT).show();
        } else if(dateTask == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddTask.this);
            builder.setTitle("Chose Date")
                    .setMessage("You forgot to chose the Date. Tasks will be automatically set on tomorrow")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dateTask = Calendar.getInstance();
                            dateTask.add(Calendar.DAY_OF_MONTH, 1);
                            saveData();
                        }
                    })
                    .setNegativeButton("Set", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            openDateMenu();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            saveData();
        }
    }

    private void openDateMenu(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DateCalendarFragment dateCalendar = new DateCalendarFragment();
        ft.replace(R.id.calendarLayout, dateCalendar);
        ft.commit();
    }

    private void saveData(){
        TasksRepository repo = ((MyApplication) getApplication()).getTasksRepository();

        if(deleteItem != null){
            repo.delete(deleteItem);
        }

        TaskEntity taskEntity = new TaskEntity(nameTask,
                descriptionTask,
                CalendarTranslator.getStringFromDate(dateTask),
                CalendarTranslator.getStringFromTime(timeStart),
                CalendarTranslator.getStringFromTime(timeEnd));
        repo.insert(taskEntity);

        Intent intent = new Intent(AddTask.this, MainActivity.class);
        startActivity(intent);
    }


    private void timeButtonListener(){
        TimeFragment newFragment = new TimeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.timeLayout, newFragment)
                .commit();
    }
    @Override
    public void SendDate(Calendar calendar) {
        dateTask = calendar;
        choseDateBtn.setText(CalendarTranslator.getStringFromDate(dateTask));
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