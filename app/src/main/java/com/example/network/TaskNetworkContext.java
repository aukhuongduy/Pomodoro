package com.example.network;

import android.util.Log;

import com.example.network.jsonmodels.GetAllTaskResponeJson;
import com.example.network.services.GetAllTaskService;
import com.example.tranh.pomodoro.database.DBContext;
import com.example.tranh.pomodoro.database.models.Task;

import java.util.ArrayList;
import java.util.List;

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
        getAllTaskService.getAllTask().enqueue(new Callback<List<GetAllTaskResponeJson>>() {
            @Override
            public void onResponse(Call<List<GetAllTaskResponeJson>> call, Response<List<GetAllTaskResponeJson>> response) {
                if (response.body() != null) {
                    for (GetAllTaskResponeJson getAllTaskResponeJson : response.body()) {
                        Task task = new Task(getAllTaskResponeJson.getLocal_id(),
                                getAllTaskResponeJson.getDue_date(),
                                getAllTaskResponeJson.getId(),
                                getAllTaskResponeJson.getName(),
                                getAllTaskResponeJson.getColor(),
                                getAllTaskResponeJson.isDone(),
                                getAllTaskResponeJson.getPaymentPerHour());
                        list.add(task);
                    }
                }else{
                    Log.e(TAG, "onResponse: "+"failure");
                }
            }

            @Override
            public void onFailure(Call<List<GetAllTaskResponeJson>> call, Throwable t) {
                Log.e(TAG, "onFailure: ");
            }
        });
        return list;
    }
}
