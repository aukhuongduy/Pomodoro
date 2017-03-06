package com.example.pomodoro.signal;

/**
 * Created by Khuong Duy on 3/4/2017.
 */

public class SignalOnCommandTimer {
    private TimerCommand timerCommand;

    public SignalOnCommandTimer(TimerCommand timerCommand) {
        this.timerCommand = timerCommand;
    }

    public TimerCommand getTimerCommand() {
        return timerCommand;
    }

    public void setTimerCommand(TimerCommand timerCommand) {
        this.timerCommand = timerCommand;
    }

    @Override
    public String toString() {
        return "SignalOnCommandTimer{" +
                "timerCommand=" + timerCommand +
                '}';
    }
}
