// Assignment : HW 07
//File Name : GroupB09_HW07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI

package com.example.groupb09_hw07;

public interface ProfileFunctionInterface {
    void gotoDelete(User muser, Photos photos);

    void gotolikedby(boolean flag, User user, User muser, Photos photos, int adapterPosition);

    void gotoComments(User muser, User mloggedinuser, Photos photos);
}
