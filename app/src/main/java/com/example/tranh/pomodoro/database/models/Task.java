package com.example.tranh.pomodoro.database.models;

/**
 * Created by Khuong Duy on 2/8/2017.
 */

public class Task {
    private String name;
    private String color;
    private boolean isDone;
    private float paymentPerHour;


    public Task(String name, String color, boolean isDone, float paymentPerHour) {
        this.name = name;
        this.color = color;
        this.isDone = isDone;
        this.paymentPerHour = paymentPerHour;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public float getPaymentPerHour() {
        return paymentPerHour;
    }

    public void setPaymentPerHour(float paymentPerHour) {
        this.paymentPerHour = paymentPerHour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", isDone=" + isDone +
                ", paymentPerHour=" + paymentPerHour +
                '}';
    }
}
