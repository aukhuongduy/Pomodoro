package com.example.tranh.pomodoro.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.database.models.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class PomodoroFragment extends Fragment {

    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public PomodoroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pomodoro, container, false);
    }

}
