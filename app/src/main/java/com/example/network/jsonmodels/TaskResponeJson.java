package com.example.network.jsonmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Khuong Duy on 2/21/2017.
 */

public class TaskResponeJson {
    @SerializedName("local_id")
    private String local_id;
    @SerializedName("name")
    private String name;
    @SerializedName("payment_per_hour")
    private float paymentPerHour;
    @SerializedName("due_date")
    private String due_date;
    @SerializedName("done")
    private boolean isDone;
    @SerializedName("id")
    private String id;
    @SerializedName("color")
    private String color;

    public TaskResponeJson(String local_id, String name, float paymentPerHour, String due_date, boolean isDone, String id, String color) {
        this.local_id = local_id;
        this.name = name;
        this.paymentPerHour = paymentPerHour;
        this.due_date = due_date;
        this.isDone = isDone;
        this.id = id;
        this.color = color;
    }

    public TaskResponeJson(String name, float paymentPerHour, boolean isDone, String color) {
        this.name = name;
        this.paymentPerHour = paymentPerHour;
        this.isDone = isDone;
        this.color = color;
    }

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPaymentPerHour() {
        return paymentPerHour;
    }

    public void setPaymentPerHour(float paymentPerHour) {
        this.paymentPerHour = paymentPerHour;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "TaskResponeJson{" +
                "local_id='" + local_id + '\'' +
                ", name='" + name + '\'' +
                ", paymentPerHour=" + paymentPerHour +
                ", due_date='" + due_date + '\'' +
                ", isDone=" + isDone +
                ", id='" + id + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
