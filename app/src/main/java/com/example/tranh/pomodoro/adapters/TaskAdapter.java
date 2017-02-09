package com.example.tranh.pomodoro.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.adapters.viewholders.TaskViewHolder;
import com.example.tranh.pomodoro.database.DBContext;
import com.example.tranh.pomodoro.database.models.Task;

import java.util.List;

/**
 * Created by Khuong Duy on 2/8/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder>{


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //1. Create View
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_task, parent,false);

        //2. Create View Holder
        TaskViewHolder taskViewHolder = new TaskViewHolder(itemView);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = DBContext.instance.allTask().get(position);
        // bind
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return DBContext.instance.allTask().size();
    }
}
