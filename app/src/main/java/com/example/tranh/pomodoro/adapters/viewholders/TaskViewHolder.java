package com.example.tranh.pomodoro.adapters.viewholders;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.database.models.Task;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by Khuong Duy on 2/8/2017.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ib_task_color)
    ImageButton ibTaskColor;

    @BindView(R.id.tv_task_name)
    TextView tvTaskName;

    @BindView(R.id.ib_play_task)
    ImageButton ibPlayTask;


    public TaskViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bind(final Task task) {
        //1: Bind bame
        tvTaskName.setText(task.getName());
        ibPlayTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(task);
            }
        });
        ibTaskColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (task.isDone()) {
                    task.setDone(false);
                    ibTaskColor.setColorFilter(Color.parseColor(task.getColor()));
                } else {
                    task.setDone(true);
                    ibTaskColor.setImageResource(R.drawable.ic_done_black_24px);
                    ibTaskColor.setColorFilter(Color.WHITE);
                }

            }
        });
        //
        GradientDrawable drawable = (GradientDrawable) ibTaskColor.getBackground();
        if (task.getColor() != null) {
            drawable.setColor(Color.parseColor(task.getColor()));
        }
        if (task.isDone()) {
            ibTaskColor.setImageResource(R.drawable.ic_done_black_24px);
        }
    }
}
