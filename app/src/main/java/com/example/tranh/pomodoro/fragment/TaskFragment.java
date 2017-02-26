package com.example.tranh.pomodoro.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.network.TaskNetworkContext;
import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.activities.TaskActivity;
import com.example.tranh.pomodoro.adapters.TaskAdapter;
import com.example.tranh.pomodoro.adapters.viewholders.TaskViewHolder;
import com.example.tranh.pomodoro.database.DBContext;
import com.example.tranh.pomodoro.database.models.Task;
import com.example.tranh.pomodoro.signal.NotidataChanged;
import com.example.tranh.pomodoro.signal.SignalGetDataSuccess;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {

    @BindView(R.id.rv_task)
    RecyclerView rvTask;

    FragmentTaskDetailListener fragmentTaskDetailListener;
    public int position;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentTaskDetailListener = (FragmentTaskDetailListener) context;

    }

    private TaskAdapter taskAdapter;

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        setUI(view);
        return view;
    }

    private void setUI(View view) {
        ButterKnife.bind(this, view);
        taskAdapter = new TaskAdapter();
        taskAdapter.setTaskItemClickListener(new TaskAdapter.TaskItemClickListener() {
            @Override
            public void onItemClick(Task task, int position) {
                replaceFragment(task, position);
            }
        });

        rvTask.setAdapter(taskAdapter);
        rvTask.setLayoutManager(new LinearLayoutManager(this.getContext()));

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Tasks");

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        rvTask.addItemDecoration(dividerItemDecoration);
        taskAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        replaceFragment(null, -1);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onDataDone(SignalGetDataSuccess signalGetDataSuccess) {
        if (signalGetDataSuccess.isDone()) {
            taskAdapter.setTaskItemClickListener(new TaskAdapter.TaskItemClickListener() {
                @Override
                public void onItemClick(Task task, int position) {
                    replaceFragment(task, position);
                }
            });

            rvTask.setAdapter(taskAdapter);
            rvTask.setLayoutManager(new LinearLayoutManager(this.getContext()));

            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setTitle("Tasks");

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
            rvTask.addItemDecoration(dividerItemDecoration);
            taskAdapter.notifyDataSetChanged();
        }

    }

    @Subscribe
    public void onDataChanged(NotidataChanged notidataChanged) {
        taskAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onDeleleClicked(TaskViewHolder.DeleteTask deleteTask) {
        showAlertDelete(deleteTask.getTask());
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void replaceFragment(Task task, int position) {
        this.position = position;
        fragmentTaskDetailListener.onReplaceTaskDetailListener(task, position);
    }

    private void showAlertDelete(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setCancelable(true).setTitle("Delete").setMessage("Delete this task").setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TaskNetworkContext.instance.deleteTask(task);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

}
