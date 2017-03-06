package com.example.pomodoro.signal;

/**
 * Created by Khuong Duy on 3/4/2017.
 */

public class SignalTimerValue {
    private long time;

    public SignalTimerValue(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SignalTimerValue{" +
                "time=" + time +
                '}';
    }
}
