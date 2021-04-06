// Assignment : HW 06
//File Name : GroupB09_HW06
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI


package com.example.groupb09_hw06;

public interface FunctionInterface {
    void deleteforum(String mforumid);

    void likedby(Forum forum, String muuid, boolean drawable);

    void gotoDetails(String muuid, Forum forum);
}
