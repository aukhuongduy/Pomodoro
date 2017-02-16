package com.example.tranh.pomodoro.fragment;

import android.support.v4.app.Fragment;

import com.example.tranh.pomodoro.database.models.Task;

/**
 * Created by Khuong Duy on 2/14/2017.
 */

public interface FragmentTaskDetailListener {
    public void onReplaceTaskDetailListener(Task task, int position);
}
