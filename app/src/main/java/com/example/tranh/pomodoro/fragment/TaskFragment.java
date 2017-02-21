package com.example.tranh.pomodoro.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.activities.TaskActivity;
import com.example.tranh.pomodoro.adapters.TaskAdapter;
import com.example.tranh.pomodoro.database.models.Task;

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
        ButterKnife.bind(this,view);
        taskAdapter = new TaskAdapter();
        taskAdapter.setTaskItemClickListener(new TaskAdapter.TaskItemClickListener() {
            @Override
            public void onItemClick(Task task, int position) {
                replaceFragment(task, position);
            }
        });

        rvTask.setAdapter(taskAdapter);
        rvTask.setLayoutManager(new LinearLayoutManager(this.getContext()));

        AppCompatActivity activity =  (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Tasks");

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        rvTask.addItemDecoration(dividerItemDecoration);
    }
    @OnClick(R.id.fab)
    void onFabClick(){
        replaceFragment(null,-1);
    }


    public void replaceFragment(Task task, int position) {
        this.position = position;
        fragmentTaskDetailListener.onReplaceTaskDetailListener(task,position);
    }

}
