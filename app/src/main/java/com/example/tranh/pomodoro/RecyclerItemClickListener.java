package com.example.tranh.pomodoro;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

/**
 * Created by Khuong Duy on 2/11/2017.
 */

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
