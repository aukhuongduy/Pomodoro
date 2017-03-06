package com.example.pomodoro.fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pomodoro.adapters.TaskAdapter;
import com.example.pomodoro.adapters.viewholders.TaskViewHolder;
import com.example.pomodoro.database.models.Task;
import com.example.pomodoro.network.TaskNetworkContext;
import com.example.pomodoro.signal.NotidataChanged;
import com.example.pomodoro.signal.ProcessDialogState;
import com.example.pomodoro.signal.SignalOnFailureNetwork;
import com.example.pomodoro.signal.SignalProcessDialog;
import com.example.tranh.pomodoro.R;
import com.example.pomodoro.signal.SignalGetDataSuccess;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Subscribe
    public void onConnectFailed(SignalOnFailureNetwork signalOnFailureNetwork) {
        showAlertNoInternet();

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
                        EventBus.getDefault().post(new SignalProcessDialog(ProcessDialogState.START));
                        TaskNetworkContext.instance.deleteTask(task);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean isMobileConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    private boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }}
        return false;
    }

    private void showAlertNoInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setCancelable(true).setTitle("Can't Connect").setMessage("Go to Setting/Internet").setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!isMobileConnected()) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setComponent(new ComponentName("com.android.settings",
                            "com.android.settings.Settings$DataUsageSummaryActivity"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if (!isWifiConnected()) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                    startActivity(intent);
                }else{
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

}
