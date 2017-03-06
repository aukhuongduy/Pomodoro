package com.example.pomodoro.database;

import android.content.Context;

import com.example.pomodoro.network.TaskNetworkContext;
import com.example.pomodoro.database.models.Color;
import com.example.pomodoro.database.models.Task;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Khuong Duy on 2/8/2017.
 */

public class DBContext {
    public Realm realm ;
    public static final  DBContext instance = new DBContext();

    public void onCreate(Context context){
        realm.init(context);
        realm = Realm.getDefaultInstance();
    }
    public DBContext() {
    }
    public Task add(Task task){
        realm.beginTransaction();
        Task taskAdded = realm.copyToRealm(task);
        realm.commitTransaction();
        return taskAdded;
    }
    public void update(String local_id,String name, float paymentPerHour, String colors, boolean isDone){
        realm.beginTransaction();
        Task oldTask = realm.where(Task.class).equalTo("local_id",local_id).findFirst();
        oldTask.setName(name);
        oldTask.setPaymentPerHour(paymentPerHour);
        oldTask.setColor(colors);
        oldTask.setDone(isDone);
        realm.commitTransaction();
    }


    public void getDBOnNetwork() {
        List<Task> list = TaskNetworkContext.instance.getAllTask();
    }

    public void addOrUpdate(Task task){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(task);
        realm.commitTransaction();
    }

    public void removeTask(Task task){
        realm.beginTransaction();
        realm.where(Task.class).equalTo("local_id",task.getLocal_id()).findFirst().deleteFromRealm();
        realm.commitTransaction();
    }


    public List<Task> allTask(){
        //Create array list
        return realm.where(Task.class).findAll();
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


}
