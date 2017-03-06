package com.example.pomodoro.decorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Khuong Duy on 2/15/2017.
 */

public class TaskColorDecor extends RecyclerView.ItemDecoration {


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top=32;
        outRect.left=32;
        outRect.right=16;
    }
}
