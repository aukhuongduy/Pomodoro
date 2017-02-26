package com.example.network;

import android.util.Log;
import android.widget.Toast;

import com.example.network.jsonmodels.DeleteTaskReponseJson;
import com.example.network.jsonmodels.TaskResponeJson;
import com.example.network.services.TaskService;
import com.example.network.services.GetAllTaskService;
import com.example.tranh.pomodoro.database.DBContext;
import com.example.tranh.pomodoro.database.models.Task;
import com.example.tranh.pomodoro.fragment.TaskDetailFragment;
import com.example.tranh.pomodoro.settings.SharedPrefs;
import com.example.tranh.pomodoro.signal.NotidataChanged;
import com.example.tranh.pomodoro.signal.SignalGetDataSuccess;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static com.example.tranh.pomodoro.fragment.TaskDetailFragment.*;

/**
 * Created by Khuong Duy on 2/21/2017.
 */

public class TaskNetworkContext extends NetworkContext {
    public static final TaskNetworkContext instance = new TaskNetworkContext();
    private OkHttpClient client;

    public TaskNetworkContext() {
        super();
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder();
        httpClient.addInterceptor(new TaskInterceptor());
        client = httpClient.build();
        retrofit = new Retrofit.Builder().
                baseUrl("http://a-task.herokuapp.com/api/").
                addConverterFactory(GsonConverterFactory.create()).client(client).
                build();
    }


    public ArrayList<Task> getAllTask() {
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
                    EventBus.getDefault().post(new SignalGetDataSuccess(true));
                    for (Task task : list) {
                        DBContext.instance.addOrUpdate(task);
                    }
                } else {
                    Log.e(TAG, "onResponse: " + "failure");
                }
            }

            @Override
            public void onFailure(Call<List<TaskResponeJson>> call, Throwable t) {

            }
        });
        return list;
    }

    public void addNewTask(final Task task) {
        TaskService taskService = retrofit.create(TaskService.class);
        MediaType jsonType = MediaType.parse("application/json");

        String createNewTaskJson = (new Gson().toJson(convertTaskResponeJson(task)));

        //
        RequestBody loginBody = RequestBody.create(jsonType, createNewTaskJson);


        Call<TaskResponeJson> createNewTaskResponeJsonCall = taskService.addNewTask(loginBody);

        createNewTaskResponeJsonCall.enqueue(new Callback<TaskResponeJson>() {
            @Override
            public void onResponse(Call<TaskResponeJson> call, Response<TaskResponeJson> response) {
                TaskResponeJson taskResponeJson = response.body();

                Log.d(TAG, "onResponse: " + taskResponeJson.toString());
            }

            @Override
            public void onFailure(Call<TaskResponeJson> call, Throwable t) {

            }
        });
    }

    public void editATask(Task task){
        TaskService taskService = retrofit.create(TaskService.class);
        MediaType jsonType = MediaType.parse("application/json");
        String taskJson = (new Gson().toJson(convertTaskResponeJson(task)));
        Log.d(TAG, "editATask: "+taskJson);
        RequestBody editTask = RequestBody.create(jsonType,taskJson);
        
        Call<TaskResponeJson> creaTaskResponeJsonCall = taskService.editATask(task.getLocal_id(),editTask);
        creaTaskResponeJsonCall.enqueue(new Callback<TaskResponeJson>() {
            @Override
            public void onResponse(Call<TaskResponeJson> call, Response<TaskResponeJson> response) {
                Log.d(TAG, "onResponse: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<TaskResponeJson> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

    }

    public void deleteTask(final Task task){
        TaskService taskService = retrofit.create(TaskService.class);
        
//        Call<DeleteTaskReponseJson> deleteTaskReponseJsonCall = taskService.deleteTask(task.getLocal_id());
        
        taskService.deleteTask(task.getLocal_id()).enqueue(new Callback<DeleteTaskReponseJson>() {
            @Override
            public void onResponse(Call<DeleteTaskReponseJson> call, Response<DeleteTaskReponseJson> response) {
                DBContext.instance.removeTask(task);
                EventBus.getDefault().post(new SignalGetDataSuccess(true));
            }

            @Override
            public void onFailure(Call<DeleteTaskReponseJson> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }


    class TaskInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            String token = SharedPrefs.getInstance().getAccessToken();
            if (token != null) {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "JWT " + token)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
            return chain.proceed(chain.request());
        }
    }


    public TaskResponeJson convertTaskResponeJson(Task task){
        TaskResponeJson taskResponeJson = new TaskResponeJson(task.getLocal_id(),
                task.getName(), task.getPaymentPerHour(),task.getDue_date(),task.isDone(),task.getId(),task.getColor());
        return taskResponeJson;
    }
}
