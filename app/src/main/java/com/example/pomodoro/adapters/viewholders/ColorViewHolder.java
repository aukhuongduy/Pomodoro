package com.example.pomodoro.adapters.viewholders;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.example.pomodoro.database.DBContext;
import com.example.pomodoro.database.models.Color;
import com.example.tranh.pomodoro.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Khuong Duy on 2/10/2017.
 */

public class ColorViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "xxxxx";
    public
    @BindView(R.id.ib_item_color)
    ImageButton imageButton;

    public boolean isSelected = false;

    public ColorViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bind(final Color color) {
        GradientDrawable drawable = (GradientDrawable) imageButton.getBackground();
        drawable.setColor(android.graphics.Color.parseColor(color.getColor()));
    }

    public void setCheck(boolean check) {
        if (check) {
            imageButton.setImageResource(R.drawable.ic_done_black_24px);
            imageButton.setColorFilter(android.graphics.Color.WHITE);
            isSelected = true;
        } else {
            imageButton.setColorFilter(android.graphics.Color.parseColor(DBContext.instance.allColor().get(getAdapterPosition()).getColor()));
            isSelected = false;
        }

    }
}
