package com.example.groupb09_hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentInterface {

    static final  String fragment="FRAGMENT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new CitiesListFragment(),fragment).commit();
    }

    @Override
    public void gotocurrentdetails(String city, String country) {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,CityCurrentFragment.newInstance(city,country),fragment).addToBackStack(null).commit();

    }

    @Override
    public void gohome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new CitiesListFragment(),fragment).commit();
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goforecast(String mcity, String mcountry) {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,ForecastFragment.newInstance(mcity,mcountry),fragment).addToBackStack(null).commit();

    }
}