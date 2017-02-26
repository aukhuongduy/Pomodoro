package com.example.tranh.pomodoro.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.adapters.viewholders.TaskViewHolder;
import com.example.tranh.pomodoro.database.DBContext;
import com.example.tranh.pomodoro.database.models.Task;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Khuong Duy on 2/8/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {


    public interface TaskItemClickListener {
        void onItemClick(Task task, int position);
    }

    private TaskItemClickListener taskItemClickListener;


    public void setTaskItemClickListener(TaskItemClickListener taskItemClickListener){
        this.taskItemClickListener = taskItemClickListener;
    }



    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //1. Create View
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_task, parent, false);

        //2. Create View Holder
        TaskViewHolder taskViewHolder = new TaskViewHolder(itemView);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, final int position) {
        final Task task = DBContext.instance.allTask().get(position);
        // bind
        holder.bind(task);
        holder.getTvTaskName().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskItemClickListener !=null){
                    taskItemClickListener.onItemClick(task,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+DBContext.instance.allTask().size());
        return DBContext.instance.allTask().size();
    }
}
