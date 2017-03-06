package com.example.pomodoro.fragment;

import com.example.pomodoro.database.models.Task;

/**
 * Created by Khuong Duy on 2/14/2017.
 */

public interface FragmentTaskDetailListener {
    public void onReplaceTaskDetailListener(Task task, int position);
}
