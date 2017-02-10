package com.example.tranh.pomodoro.database.models;

/**
 * Created by Khuong Duy on 2/10/2017.
 */

public class Color {
    String color;
    boolean isSelected;
    public Color(String color, boolean isSelected) {
        this.color = color;
        this.isSelected =isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Color{" +
                "color='" + color + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
