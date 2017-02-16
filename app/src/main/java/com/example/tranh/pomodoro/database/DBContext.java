package com.example.tranh.pomodoro.database;

import com.example.tranh.pomodoro.database.models.Color;
import com.example.tranh.pomodoro.database.models.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khuong Duy on 2/8/2017.
 */

public class DBContext {

    public  ArrayList<Task> tasks = new ArrayList<>();

    public DBContext() {
        setDB();
    }

    private void setDB() {
        tasks.add(new Task("Study RecyclerView","#F4511E",true,25));
        tasks.add(new Task("Practice RecyclerView","#6D4C41",true,21.5f));
        tasks.add(new Task("Practice Networking","#757575",false, 16.5f));
        tasks.add(new Task("Doing Homework","#FFB300",false,13.7f));
        tasks.add(new Task("Study API","#FDD835",false,64f));
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
