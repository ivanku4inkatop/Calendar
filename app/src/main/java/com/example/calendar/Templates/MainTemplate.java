package com.example.calendar.Templates;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calendar.AddTask.AddTask;
import com.example.calendar.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public abstract class MainTemplate extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_template);

        LayoutInflater inflater = LayoutInflater.from(this);
        View uniqueContent = inflater.inflate(getLayoutResId(), findViewById(R.id.template_content), true);

        FloatingActionButton addTask = findViewById(R.id.addTask);
        addTask.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddTask.class);
            startActivity(intent);
        });
    }

    @LayoutRes
    protected abstract int getLayoutResId();
}
