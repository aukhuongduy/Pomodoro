package com.example.tranh.pomodoro.database;

import com.example.tranh.pomodoro.database.models.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khuong Duy on 2/8/2017.
 */

public class DBContext {

    public static final DBContext instance = new DBContext();

    public List<Task> allTask(){
        //Create array list
        ArrayList<Task> tasks = new ArrayList<>();

        //
        tasks.add(new Task("Study RecyclerView","#F4511E"));
        tasks.add(new Task("Practice RecyclerView","#6D4C41"));
        tasks.add(new Task("Practice Networking","#757575"));
        tasks.add(new Task("Doing Homework","#FFB300"));
        tasks.add(new Task("Study API","#FDD835"));
        return tasks;
    }



}
