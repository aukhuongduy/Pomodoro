package com.example.network;

import android.util.Log;

import com.example.network.jsonmodels.LoginBodyJson;
import com.example.network.jsonmodels.TaskResponeJson;
import com.example.network.services.CreateNewTaskService;
import com.example.network.services.GetAllTaskService;
import com.example.tranh.pomodoro.database.DBContext;
import com.example.tranh.pomodoro.database.models.Task;
import com.example.tranh.pomodoro.fragment.TaskDetailFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Khuong Duy on 2/21/2017.
 */

public class TaskNetworkContext extends NetworkContext {
    public static final TaskNetworkContext instance = new TaskNetworkContext();

    public TaskNetworkContext() {
    }

    public ArrayList<Task> getAllTask(){
        final GetAllTaskService getAllTaskService = retrofit.create(GetAllTaskService.class);

        final ArrayList<Task> list = new ArrayList<>();
        getAllTaskService.getAllTask().enqueue(new Callback<List<TaskResponeJson>>() {
            @Override
            public void onResponse(Call<List<TaskResponeJson>> call, Response<List<TaskResponeJson>> response) {
                if (response.body() != null) {
                    for (TaskResponeJson taskResponeJson : response.body()) {
                        Task task = new Task(taskResponeJson.getLocal_id(),
                                taskResponeJson.getDue_date(),
                                taskResponeJson.getId(),
                                taskResponeJson.getName(),
                                taskResponeJson.getColor(),
                                taskResponeJson.isDone(),
                                taskResponeJson.getPaymentPerHour());
                        list.add(task);
                    }
                }else{
                    Log.e(TAG, "onResponse: "+"failure");
                }
            }

            @Override
            public void onFailure(Call<List<TaskResponeJson>> call, Throwable t) {

            }
        });
        return list;
    }

    public void addNewTask(final Task task){
        CreateNewTaskService createNewTaskService = retrofit.create(CreateNewTaskService.class);
        MediaType jsonType = MediaType.parse("application/json");

        String createNewTaskJson = (new Gson().toJson(new TaskResponeJson(task.getName(),task.getPaymentPerHour(),task.isDone(),task.getColor())));

        //
        RequestBody loginBody = RequestBody.create(jsonType, createNewTaskJson);


        Call<TaskResponeJson> createNewTaskResponeJsonCall = createNewTaskService.addNewTask(loginBody);

        createNewTaskResponeJsonCall.enqueue(new Callback<TaskResponeJson>() {
            @Override
            public void onResponse(Call<TaskResponeJson> call, Response<TaskResponeJson> response) {
                TaskResponeJson taskResponeJson =response.body();
                Log.d(TAG, "onResponse: "+taskResponeJson.toString());
            }
            @Override
            public void onFailure(Call<TaskResponeJson> call, Throwable t) {

            }
        });
    }
}
