package com.example.demoapplication;

public class Person {

     String id;
     String name;
     String age;

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", address=" + address +
                '}';
    }



     Address address;
}
