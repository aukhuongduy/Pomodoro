package com.example.tranh.pomodoro.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.adapters.ColorAdapter;
import com.example.tranh.pomodoro.database.DBContext;
import com.example.tranh.pomodoro.database.models.Task;
import com.example.tranh.pomodoro.decorations.TaskColorDecor;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {


    private String title;
    private Task task;
    private int positionTask;


    ColorAdapter colorAdapter;
    @BindView(R.id.rv_colors)
    RecyclerView rv_colors;
    @BindView(R.id.et_Name)
    EditText et_name;
    @BindView(R.id.et_payment_per_hour)
    EditText et_payment_per_hour;
    @BindView(R.id.sw_isDone)
    Switch sw_isDone;
    @BindView(R.id.tv_isDone)
    TextView tv_isDone;


    public TaskDetailFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setPositionTask(int positionTask) {
        this.positionTask = positionTask;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        setUI(view);
        return view;
    }

    private void setUI(View view) {
        ButterKnife.bind(this, view);
        colorAdapter = new ColorAdapter();
        rv_colors.setAdapter(colorAdapter);
        CustomGridLayoutManager customGridLayoutManager = new CustomGridLayoutManager(this.getContext(), 4);
        customGridLayoutManager.setScrollEnabled(false);
        rv_colors.setLayoutManager(customGridLayoutManager);
        rv_colors.addItemDecoration(new TaskColorDecor());
        et_payment_per_hour.setText("0.0");
        et_payment_per_hour.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (et_payment_per_hour.getText() == null) {
                    et_payment_per_hour.setHint("");
                }
            }
        });
        sw_isDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(task.isDone()){
                    tv_isDone.setText("This task is working");
                }else {
                    tv_isDone.setText("This task is done");
                }
            }
        });
        if (task != null) {
            et_name.setText(task.getName());
            et_payment_per_hour.setText(String.format("%.1f", task.getPaymentPerHour()));
            sw_isDone.setChecked(task.isDone());
            if(task.isDone()){
                tv_isDone.setText("This task is working");
            }else {
                tv_isDone.setText("This task is done");
            }
            Log.d(TAG, String.format("setUI: %s", task.getColor()));
            colorAdapter.setSelectedColor(task.getColor());
        }else{
            tv_isDone.setVisibility(View.GONE);
            sw_isDone.setVisibility(View.GONE);
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(title);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.i_OK) {

            String taskName = et_name.getText().toString();
            float paymentPerHour = Float.parseFloat(et_payment_per_hour.getText().toString());
            String color = colorAdapter.getSelectedColor();
            boolean isDone = sw_isDone.isChecked();
            Task newtask = new Task(taskName, color, isDone,paymentPerHour);

            if (positionTask == -1) {
                DBContext.instance.tasks.add(newtask);
            } else {
                DBContext.instance.tasks.set(positionTask, newtask);
            }
            getActivity().onBackPressed();
        }

        return false;
    }


}
