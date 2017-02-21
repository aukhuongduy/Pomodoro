package com.example.tranh.pomodoro.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.adapters.viewholders.ColorViewHolder;
import com.example.tranh.pomodoro.database.DBContext;
import com.example.tranh.pomodoro.database.models.Color;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Khuong Duy on 2/10/2017.
 */

public class ColorAdapter extends RecyclerView.Adapter<ColorViewHolder> {

    private int selectedPosition;

    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_color,parent,false);

        ColorViewHolder colorViewHolder= new ColorViewHolder(itemView);

        return colorViewHolder;
    }
    public String getSelectedColor(){
        return DBContext.instance.allColor().get(selectedPosition).getColor();
    }

    public void setSelectedColor(String color){
        for(int colorIndex =0; colorIndex< DBContext.instance.allColor().size();colorIndex++){
            if(DBContext.instance.allColor().get(colorIndex).getColor().equalsIgnoreCase(color)){
                selectedPosition =colorIndex;
            }

        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, final int position) {
        final Color  color = DBContext.instance.allColor().get(position);
        holder.bind(color);
//        imageButton.setImageResource(R.drawable.ic_done_black_24px);
//        imageButton.setColorFilter(android.graphics.Color.WHITE);
//        isSelected = true;}else{
//        imageButton.setColorFilter(android.graphics.Color.parseColor(DBContext.instance.allColor().get(getAdapterPosition()).getColor()));
//        isSelected = false;

        if(selectedPosition == position){
            holder.setCheck(true);
        }else{
            holder.setCheck(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return DBContext.instance.allColor().size();
    }


}
