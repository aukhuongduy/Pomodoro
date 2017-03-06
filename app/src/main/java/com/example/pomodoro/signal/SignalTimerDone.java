package com.example.pomodoro.signal;

/**
 * Created by Khuong Duy on 3/7/2017.
 */

public class SignalTimerDone {
    boolean isDone;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public SignalTimerDone(boolean isDone) {

        this.isDone = isDone;
    }
}
