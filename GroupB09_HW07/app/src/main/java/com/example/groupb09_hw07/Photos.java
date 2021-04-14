// Assignment : HW 07
//File Name : GroupB09_HW07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI



package com.example.groupb09_hw07;




import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Photos implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPhotoid() {
        return photoid;
    }

    public void setPhotoid(String photoid) {
        this.photoid = photoid;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<String> getLikedby() {
        return likedby;
    }

    public void setLikedby(List<String> likedby) {
        this.likedby = likedby;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String uuid;
    String photoid;
    String Caption;
    List<Comment> commentList;
    List<String> likedby;
    String date;

    @Override
    public String toString() {
        return "Photos{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", photoid='" + photoid + '\'' +
                ", Caption='" + Caption + '\'' +
                ", commentList=" + commentList +
                ", likedby=" + likedby +
                ", date='" + date + '\'' +
                '}';
    }
}
