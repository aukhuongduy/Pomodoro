package com.example.tranh.pomodoro.adapters.viewholders;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.database.models.Task;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Khuong Duy on 2/8/2017.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.v_task_color)
    View vTaskColor;

    @BindView(R.id.tv_task_name)
    TextView tvTaskName;

    public TaskViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(Task task) {
        //1: Bind bame
       tvTaskName.setText(task.getName());
        //
        GradientDrawable drawable = (GradientDrawable) vTaskColor.getBackground();
        drawable.setColor(Color.parseColor(task.getColor()));

    }
}
