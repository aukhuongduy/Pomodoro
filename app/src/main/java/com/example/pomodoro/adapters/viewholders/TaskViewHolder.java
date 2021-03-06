package com.example.pomodoro.adapters.viewholders;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pomodoro.database.DBContext;
import com.example.pomodoro.signal.ProcessDialogState;
import com.example.pomodoro.signal.SignalProcessDialog;
import com.example.tranh.pomodoro.R;
import com.example.pomodoro.database.models.Task;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @BindView(R.id.ib_deleteTask)
    ImageButton ibDeleteTask;

    public TaskViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public TextView getTvTaskName() {
        return tvTaskName;
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
                    DBContext.instance.realm.beginTransaction();
                    task.setDone(false);
                    ibTaskColor.setColorFilter(Color.parseColor(task.getColor()));
                    DBContext.instance.realm.commitTransaction();
                } else {
                    DBContext.instance.realm.beginTransaction();
                    task.setDone(true);
                    ibTaskColor.setImageResource(R.drawable.ic_done_black_24px);
                    ibTaskColor.setColorFilter(Color.WHITE);
                    DBContext.instance.realm.commitTransaction();
                }

            }
        });
        ibDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteTask(task));

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
    public class DeleteTask{
        private Task task;

        public DeleteTask(Task task) {
            this.task = task;
        }

        public Task getTask() {
            return task;
        }
    }
}
