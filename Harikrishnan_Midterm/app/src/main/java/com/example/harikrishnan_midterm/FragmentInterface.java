package com.example.harikrishnan_midterm;

public interface FragmentInterface {
    void gotoforums(DataServices.Account account, String token);

    void gotocreate();

    void goHome();


    void gonewforum(String mtoken, DataServices.Account maccount);

    void gotodetails(DataServices.Account maccount, String mtoken, long forumId,DataServices.Forum forum);
}
