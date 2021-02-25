package com.example.demoapplication;

public class User {
    public String name;
    public int age;
    public String gender;

    public User(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return name + " , " + Integer.toString(age)+" , "+gender;
    }
}
