// Assignment : In Class 07
//File Name : GroupB09_InClass07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.groupb09inclass07;

import android.os.Parcelable;

import java.io.Serializable;

public class Contact implements Serializable {
    public String id;
    public String name;
    public String type;
    public String phone;
    public String email;

    public Contact() {

    }


    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Contact(String id, String name, String type, String phone, String email) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.email = email;
    }


}
