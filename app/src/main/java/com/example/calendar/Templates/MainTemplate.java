package com.example.calendar.Templates;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.calendar.BasicTasks.AddTask.AddTask;
import com.example.calendar.MainActivity;
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
        ImageButton backButton = findViewById(R.id.backButton);
        ImageButton sidebarButton = findViewById(R.id.sidebarButton);
        View overlay = findViewById(R.id.sidebarOverlay);

        addTask.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddTask.class);
            startActivity(intent);
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        sidebarButton.setOnClickListener(v -> {
            overlay.setVisibility(View.VISIBLE);
            overlay.bringToFront();
            findViewById(R.id.sidebarLayout).bringToFront();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.sidebarLayout, new Sidebar())
                    .commit();
        });


        overlay.setOnClickListener(v -> {
            overlay.setVisibility(View.GONE);

            Fragment sidebar = getSupportFragmentManager()
                    .findFragmentById(R.id.sidebarLayout);

            if (sidebar != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(sidebar)
                        .commit();
            }
        });

    }

    @LayoutRes
    protected abstract int getLayoutResId();
}
