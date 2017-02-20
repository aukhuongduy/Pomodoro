package com.example.tranh.pomodoro.database;

import android.util.Log;

import com.example.network.NetworkContext;
import com.example.network.TaskNetworkContext;
import com.example.tranh.pomodoro.database.models.Color;
import com.example.tranh.pomodoro.database.models.Task;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Khuong Duy on 2/8/2017.
 */

public class DBContext {

    public  ArrayList<Task> tasks = new ArrayList<>();

    public DBContext() {
        setDB();
    }

    private void setDB() {
        tasks = TaskNetworkContext.instance.getAllTask();
        for (Task task : tasks) {
            Log.d(TAG, String.format("setDB: %s", task.toString()));
        }
    }


    public static final DBContext instance = new DBContext();

    public List<Task> allTask(){
        //Create array list
        return tasks;
    }

    public List<Color> allColor(){
        ArrayList<Color> colors = new ArrayList<>();

        colors.add(new Color("#F44336",false));
        colors.add(new Color("#E91E63",false));
        colors.add(new Color("#9C27B0",false));
        colors.add(new Color("#9E9E9E",false));
        colors.add(new Color("#3F51B5",false));
        colors.add(new Color("#2196F3",false));
        colors.add(new Color("#4CAF50",false));
        colors.add(new Color("#FFEB3B",false));

        return colors;
    }


    public void addTask(Task newtask) {
        tasks.add(newtask);
    }
}
