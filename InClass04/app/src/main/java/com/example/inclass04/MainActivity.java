// Assignment : In Class 04
//File Name : InClass04
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.inclass04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements FragmentInterface {

    public static String Fragment="Fragment";
    DataServices.Account account1;
    Button buttonCreateAcc;
    public static final String key="KEY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.mainContainer,new LoginFragment(),Fragment).commit();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onLogin(DataServices.Account account) {
        account1=account;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,ProfileFragment.newInstance(account),Fragment).addToBackStack(null).commit();
    }

    @Override
    public void onCreate() {
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,new RegisterFragment(),Fragment).addToBackStack(null).commit();
    }

    @Override
    public void profile(DataServices.Account account) {
        account1=account;
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,ProfileFragment.newInstance(account),Fragment).addToBackStack(null).commit();
    }

    @Override
    public void home() {
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,new LoginFragment(),Fragment).commit();



    }

    @Override
    public void Editprofile(DataServices.Account account) {

        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,UpdateFragment.newInstance(account),Fragment).addToBackStack(null).commit();


    }
}