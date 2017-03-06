package com.example.pomodoro.settings;

import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Khuong Duy on 1/17/2017.
 */

public class SettingCredentials {
    public static final int[] WORKTIMEVALUES = new int[] {1,2,5,10,15,25,35,50,65,80,100};
    public static final int[] BREAKTIMEVALUES = new int[] {5,10,15,20,25,30,35,40,45,50,60};
    public static final int[] LONGTIMEVALUES = new int[] {10,15,20,25,30,45,60,75,90,120,150};
    public static final String[] TIMETOLONGBREAKS = new String[]{"1","2","3","4","5"};
    private int workTime;
    private int breakTime;
    private int longBreak;
    private int countToLongBreak;

    public int getSpinnerValue(int value, int[] list){
        for (int i = 0; i <list.length ; i++) {
            if(value == list[i]){
                return i;
            }
        }

        return -1;
    }
    public int getSpinnerValue(int value, String[] list){
        for (int i = 0; i <list.length ; i++) {
            Log.d(TAG, "getSpinnerValue: "+i);
            if(Integer.parseInt(list[i]) == value){

                return i;

            }
        }

        return -1;
    }

    public SettingCredentials(int workTime, int breakTime, int longBreak, int countToLongBreak) {
        this.countToLongBreak = countToLongBreak;
        this.longBreak = longBreak;
        this.workTime = workTime;
        this.breakTime = breakTime;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public int getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(int breakTime) {
        this.breakTime = breakTime;
    }

    public int getLongBreak() {
        return longBreak;
    }

    public void setLongBreak(int longBreak) {
        this.longBreak = longBreak;
    }

    public int getCountToLongBreak() {
        return countToLongBreak;
    }

    public void setCountToLongBreak(int countToLongBreak) {
        this.countToLongBreak = countToLongBreak;
    }

}
