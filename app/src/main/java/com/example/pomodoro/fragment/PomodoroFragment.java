package com.example.pomodoro.fragment;


import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pomodoro.database.models.Task;
import com.example.pomodoro.signal.SignalOnCommandTimer;
import com.example.pomodoro.signal.SignalTimerValue;
import com.example.tranh.pomodoro.R;
import com.example.pomodoro.signal.TimerCommand;
import com.github.lzyzsd.circleprogress.CircleProgress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PomodoroFragment extends Fragment {

    @BindView(R.id.ib_play_pause)
    ImageButton ibPlayPause;
    @BindView(R.id.tv_timer)
    TextView tvTimer;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.ib_next)
    ImageButton ibNext;
    @BindView(R.id.ib_stop)
    ImageButton ibStop;
    private PomodoroState pomodoroState = PomodoroState.START;
    private boolean isBreak = false;

    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public PomodoroFragment() {

        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pomodoro, container, false);
        ButterKnife.bind(this, view);

        ibPlayPause.setOnClickListener(new View.OnClickListener() {
            public static final String TAG = "xxx";

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + pomodoroState);
                switch (pomodoroState) {
                    case START:
                        ibPlayPause.setImageResource(R.drawable.ic_pause_brown_24dp);
                        EventBus.getDefault().post(new SignalOnCommandTimer(TimerCommand.START));
                        pomodoroState = PomodoroState.PAUSE;
                        break;
                    case PAUSE:
                        ibPlayPause.setImageResource(R.drawable.ic_play_arrow_brown_24px);
                        EventBus.getDefault().post(new SignalOnCommandTimer(TimerCommand.PAUSE));
                        pomodoroState = PomodoroState.RESUME;
                        break;
                    case RESUME:
                        ibPlayPause.setImageResource(R.drawable.ic_pause_brown_24dp);
                        EventBus.getDefault().post(new SignalOnCommandTimer(TimerCommand.RESUME));
                        pomodoroState = PomodoroState.PAUSE;
                        break;
                }
            }
        });
        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBreak) {
                    EventBus.getDefault().post(new SignalOnCommandTimer(TimerCommand.START));
                    isBreak =false;
                } else {
                    EventBus.getDefault().post(new SignalOnCommandTimer(TimerCommand.BREAK));
                    isBreak=true;
                }
                ibPlayPause.setImageResource(R.drawable.ic_pause_brown_24dp);
                pomodoroState = PomodoroState.PAUSE;
            }
        });
        ibStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new SignalOnCommandTimer(TimerCommand.STOP));
                onDoneAll();
            }
        });
        return view;
    }

    @Subscribe
    public void onDoneAll(){
        ibPlayPause.setImageResource(R.drawable.ic_play_arrow_brown_24px);
        tvTimer.setText("25 : 00");
        pomodoroState = PomodoroState.START;
        progressBar.setProgress(0);
        Toast.makeText(this.getContext(), "Done your Task", Toast.LENGTH_SHORT).show();
    }

    @Subscribe(sticky = true)
    public void onTick(SignalTimerValue signalTimerValue) {
        if (!isBreak) {
            progressBar.setProgress(1500 - (int) (signalTimerValue.getTime() / 1000));
        } else {
            progressBar.setProgress(1500 - (int) (signalTimerValue.getTime() / 200));
        }
        int min = (int) signalTimerValue.getTime() / 60000;
        int sec = (int) signalTimerValue.getTime() / 1000 - (min * 60);
        tvTimer.setText(min + " : " + sec);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
