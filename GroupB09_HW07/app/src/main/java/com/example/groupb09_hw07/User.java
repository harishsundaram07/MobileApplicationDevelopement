// Assignment : HW 07
//File Name : GroupB09_HW07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI




package com.example.groupb09_hw07;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    String name;

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    String profilepic;

    public ArrayList<Photos> getPhotosList() {
        return photosList;
    }

    public void setPhotosList(ArrayList<Photos> photosList) {
        this.photosList = photosList;
    }

    ArrayList<Photos> photosList;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", emailid='" + emailid + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    String emailid;
    String uuid;
}
