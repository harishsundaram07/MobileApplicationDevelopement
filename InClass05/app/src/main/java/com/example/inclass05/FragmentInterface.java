package com.example.inclass05;

public interface FragmentInterface {


    public void home();

    void goProfile(String token);

    void goRegister();

    void goAppList(String s, String token);

    void goAppDetails(DataServices.App app);
}
