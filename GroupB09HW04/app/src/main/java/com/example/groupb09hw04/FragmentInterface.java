// Assignment : Homework 03
//File Name : HW03
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.groupb09hw04;

public interface FragmentInterface {


    public void home();

    void goProfile(String token);

    void goRegister();

    void goAppList(String s, String token);

    void goAppDetails(DataServices.App app);
}
