package com.example.hw02;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class Task implements Parcelable {

    String name;
    String date;
    String priority;

    public Task(String name, String date, String priority) {
        this.name = name;
        this.date = date;
        this.priority = priority;

    }

    protected Task(Parcel in) {
        name = in.readString();
        date = in.readString();
        priority = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(priority);
    }
}

