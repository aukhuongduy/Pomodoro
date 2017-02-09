package com.example;

import android.app.Application;
import android.util.Log;

import com.example.tranh.pomodoro.database.DBContext;
import com.example.tranh.pomodoro.database.models.Task;
import com.example.tranh.pomodoro.settings.SharedPrefs;

/**
 * Created by Khuong Duy on 1/14/2017.
 */

public class PomodoroApplication extends Application{
    private  static  final  String TAG = "PomodoroApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        SharedPrefs.init(this);

        for(Task task : DBContext.instance.allTask()){
            Log.d(TAG, String.format("onCreate: %s",task));
        }


    }
}
