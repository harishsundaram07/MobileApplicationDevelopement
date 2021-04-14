// Assignment : HW 07
//File Name : GroupB09_HW07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI





package com.example.groupb09_hw07;

public interface FragmentInterface {
    void goList(String uuid);

    void goRegister();

    void goHome();


    void goDetails(User uuid, User user);

    void goRegister(User user);

    void goaddphoto(User muser);

    void gotocomment(User muser, User mloggedinuser, Photos photos);
}
