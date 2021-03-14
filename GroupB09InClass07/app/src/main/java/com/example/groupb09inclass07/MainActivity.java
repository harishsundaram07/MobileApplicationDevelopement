// Assignment : In Class 07
//File Name : GroupB09_InClass07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI

package com.example.groupb09inclass07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentInterface{


    public final static String fragment="FRAGMENT";
    public final static String key="KEY";
    String backstack="backstack";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.maincontainer,new ContactListFragment(),fragment).commit();

    }

    @Override
    public void goDetails(Contact contact) {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, DetailsFragment.newInstance(contact),fragment).addToBackStack(null).commit();
        getSupportFragmentManager().popBackStack(backstack,getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);

    }



    @Override
    public void goCreate() {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new CreateContact(),fragment).addToBackStack(null).commit();

    }

    @Override
    public void gohome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new ContactListFragment(),fragment).commit();
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void goUpdate(Contact mcontact) {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,UpdateFragment.newInstance(mcontact),fragment).addToBackStack(backstack).commit();
    }




}