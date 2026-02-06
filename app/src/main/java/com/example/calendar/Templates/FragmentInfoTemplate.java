package com.example.calendar.Templates;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.calendar.BasicTasks.TasksList.TaskInfoFragment;
import com.example.calendar.R;

public abstract class FragmentInfoTemplate extends Fragment {
    private TextView name;
    private Button close;
    private FrameLayout wrap;
    private Button edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_template, container, false);
        close = view.findViewById(R.id.closeTemp);
        name = view.findViewById(R.id.textNameTemp);
        wrap = view.findViewById(R.id.wrapTemp);
        edit = view.findViewById(R.id.buttonEditTemp);
        setupBase();

        return view;
    }

    private void setupBase(){
        close.setOnClickListener(v ->{
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.remove(FragmentInfoTemplate.this);
            ft.commit();
        });

        edit.setOnClickListener(v -> {
            setEditButton();
        });
    }

    protected abstract void setEditButton();


    protected void setTitleText(String text) {
        if (name != null) {
            name.setText(text);
        }
    }

    protected FrameLayout getWrap() {
        return wrap;
    }


}