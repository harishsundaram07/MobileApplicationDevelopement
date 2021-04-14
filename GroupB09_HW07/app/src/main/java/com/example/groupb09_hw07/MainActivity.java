package com.example.groupb09_hw07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentInterface {
    final String fragment="FRAGMENT";
    final String newforum="NEWFORUM";
    final String newforum1="NEWFORUM1";
    final String newforum2="NEWFORUM2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new LoginFragment(),fragment).commit();
    }

    @Override
    public void goList(String uuid) {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,UserListFragment.newInstance(uuid),fragment).commit();
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new RegisterFragment(),fragment).addToBackStack(newforum1).commit();

    }

    @Override
    public void goHome() {
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new LoginFragment(),fragment).commit();
    }


    @Override
    public void goDetails(User user, User loggedinuser) {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,ProfileFragment.newInstance(user,loggedinuser),fragment).addToBackStack(null).commit();
    }

    @Override
    public void goRegister(User user) {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,RegisterFragment.newInstance(user),fragment).addToBackStack(newforum1).commit();

    }

    @Override
    public void goaddphoto(User muser) {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,AddPhotoFragment.newInstance(muser,null),fragment).commit();

    }

    @Override
    public void gotocomment(User muser, User mloggedinuser, Photos photos) {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,CommentsFragment.newInstance(photos,muser,mloggedinuser),fragment).addToBackStack(null).commit();

    }
}