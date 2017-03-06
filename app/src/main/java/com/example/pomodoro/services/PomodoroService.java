package com.example.pomodoro.services;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.pomodoro.signal.SignalOnCommandTimer;
import com.example.pomodoro.signal.SignalTimerDone;
import com.example.pomodoro.signal.SignalTimerValue;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static android.content.ContentValues.TAG;

/**
 * Created by Khuong Duy on 3/4/2017.
 */

public class PomodoroService extends Service {
    private CountDownTimer countDownTimer;
    private long time;
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Subscribe
    public void onCommand(SignalOnCommandTimer signalOnCommandTimer){
        switch (signalOnCommandTimer.getTimerCommand()){
            case START:
                startTimer();
                break;
            case PAUSE:
                pauseTimer();
                break;
            case RESUME:
                resumeTimer();
                break;
            case BREAK:
                startBreakTime();
                break;
            case STOP:
                stopTimer();
                break;
        }
    }
    private void startTimer(){
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
        this.countDownTimer = new CountDownTimer(1500000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = millisUntilFinished;
                EventBus.getDefault().postSticky(new SignalTimerValue(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                EventBus.getDefault().post(new SignalTimerDone(true));
            }
        };
        this.countDownTimer.start();
    }
    private void pauseTimer(){
        this.countDownTimer.cancel();
        this.countDownTimer = new CountDownTimer(time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = millisUntilFinished;
                EventBus.getDefault().postSticky(new SignalTimerValue(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                EventBus.getDefault().post(new SignalTimerDone(true));
            }
        };
    }
    private void resumeTimer(){
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
        this.countDownTimer.start();
    }
    private void stopTimer(){
        this.countDownTimer.cancel();
        this.countDownTimer.onFinish();
    }
    private void startBreakTime(){
        this.countDownTimer.cancel();
        this.countDownTimer = new CountDownTimer(300000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = millisUntilFinished;
                EventBus.getDefault().postSticky(new SignalTimerValue(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                EventBus.getDefault().post(new SignalTimerDone(true));
            }
        };
        this.countDownTimer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
