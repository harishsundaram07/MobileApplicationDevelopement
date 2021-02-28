// Assignment : In Class 05
//File Name : InClass05
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.hw03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentInterface{

    public static final String fragment="Fragment";
    DataServices.Account mainaccount;
    public static final String key="KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.mainContainer,new LoginFragment(),fragment).commit();


    }

    @Override
    public void home() {
        mainaccount=null;
        //TODO
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,new LoginFragment(),fragment).commit();
    }

    @Override
    public void goProfile(String token) {
        getSupportFragmentManager().popBackStack();

        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, CategoriesFragment.newInstance(token),fragment).commit();

    }

    @Override
    public void goRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,new RegisterFragment(),fragment).addToBackStack(null).commit();
    }

    @Override
    public void goAppList(String category, String token) {
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,AppListFragment.newInstance(token,category),fragment).addToBackStack(null).commit();
    }

    @Override
    public void goAppDetails(DataServices.App app) {
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,AppDetailsFragment.newInstance(app),fragment).addToBackStack(null).commit();
    }

}