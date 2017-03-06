package com.example.pomodoro.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Khuong Duy on 3/4/2017.
 */

public class LandscapeSquareLayout extends RelativeLayout {
    public LandscapeSquareLayout(Context context) {
        super(context);
    }

    public LandscapeSquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LandscapeSquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height  = MeasureSpec.getSize(heightMeasureSpec);
        int width = height;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
