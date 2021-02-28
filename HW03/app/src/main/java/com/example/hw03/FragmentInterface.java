// Assignment : In Class 05
//File Name : InClass05
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.hw03;

public interface FragmentInterface {


    public void home();

    void goProfile(String token);

    void goRegister();

    void goAppList(String s, String token);

    void goAppDetails(DataServices.App app);
}
