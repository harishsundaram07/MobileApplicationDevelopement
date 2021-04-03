// Assignment : Homework 05
//File Name : GroupB09_HW05
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI

package com.example.groupb09_hw05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupb09_hw05.datamodel.Forecast;
import com.example.groupb09_hw05.datamodel.WeatherCurrent;

import java.util.ArrayList;


public class ForecastFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mcity;
    private String mcountry;
    FragmentInterface fragmentInterface;
    public ArrayList<Forecast.List> list;
    View view;
    CityForecastRecylerAdapter cityForecastRecylerAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerforecast;
    TextView textVietitle1;


    public ForecastFragment() {

    }

    public static ForecastFragment newInstance(String city,String country) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, city);
        args.putString(ARG_PARAM2, country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mcity = getArguments().getString(ARG_PARAM1);
            mcountry = getArguments().getString(ARG_PARAM2);


        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentInterface )
            fragmentInterface= (FragmentInterface) context;
        else
            throw new RuntimeException(context.toString()+" must be implemented");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_forecast, container, false);
        recyclerforecast=view.findViewById(R.id.recyclerforecast);
        recyclerforecast.setHasFixedSize(true);
        getActivity().setTitle(R.string.weatherforecast);
        textVietitle1=view.findViewById(R.id.textVietitle1);
        textVietitle1.setText(mcity+", "+mcountry);

        CallAPI.getforecast(mcity, new CallAPI.CallHandler() {

            @Override
            public void onSuccess(Object o, String message) {
                Forecast forecast= (Forecast) o;
                if(getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cityForecastRecylerAdapter = new CityForecastRecylerAdapter(forecast.list);
                            linearLayoutManager = new LinearLayoutManager(view.getContext());
                            recyclerforecast.setLayoutManager(linearLayoutManager);
                            recyclerforecast.setAdapter(cityForecastRecylerAdapter);
                        }
                    });
                }

            }

            @Override
            public void onFailure(String message) {
                if(getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            fragmentInterface.gotocurrentdetails(mcity, mcountry);
                        }
                    });
                }

            }
        });




        return view;
    }
}