package com.example.tranh.pomodoro.database.models;

/**
 * Created by Khuong Duy on 2/8/2017.
 */

public class Task {
    private String local_id;
    private String due_date;
    private String id;
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

    public Task(String local_id, String due_date, String id, String name, String color, boolean isDone, float paymentPerHour) {
        this.local_id = local_id;
        this.due_date = due_date;
        this.id = id;
        this.name = name;
        this.color = color;
        this.isDone = isDone;
        this.paymentPerHour = paymentPerHour;
    }

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                "local_id='" + local_id + '\'' +
                ", due_date='" + due_date + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", isDone=" + isDone +
                ", paymentPerHour=" + paymentPerHour +
                '}';
    }
}
