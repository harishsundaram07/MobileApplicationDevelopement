/// Assignment : In Class 03
//File Name : User.java
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.inclass03;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    String name;
    String email;
    String id;
    String dept;

    public User(String name, String email, String id, String dept) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.dept = dept;
    }

    public User() {
    }

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        id = in.readString();
        dept = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(id);
        dest.writeString(dept);
    }
}
