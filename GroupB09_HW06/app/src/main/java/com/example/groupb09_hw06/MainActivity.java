// Assignment : InClass 08
//File Name : GroupB09_InClass08
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI


package com.example.groupb09_hw06;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements  FragmentInterface{
    final String fragment="FRAGMENT";
    final String newforum="NEWFORUM";
    final String newforum1="NEWFORUM1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new LoginFragment(),fragment).commit();

    }

    @Override
    public void goList(String uuid) {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,ForumListFragment.newInstance(uuid),fragment).addToBackStack(null).commit();
        getSupportFragmentManager().popBackStack(newforum, FragmentManager.POP_BACK_STACK_INCLUSIVE);


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
    public void gonewforum(User muser) {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,NewForumFragment.newInstance(muser),fragment).addToBackStack(newforum).commit();

    }

    @Override
    public void goDetails(String uuid,User user, Forum forum) {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,DetailsForumFragment.newInstance(uuid,forum,user),fragment).addToBackStack(null).commit();

    }


}