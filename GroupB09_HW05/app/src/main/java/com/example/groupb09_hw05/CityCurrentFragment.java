package com.example.groupb09_hw05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupb09_hw05.datamodel.WeatherCurrent;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class CityCurrentFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;
    ArrayList<Data.City> cities=new ArrayList<>();
    CitylistRecylerAdapter citylistRecylerAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerViewcity;
    FragmentInterface fragmentInterface;
    TextView textViewTemperature;
    TextView textViewTemperatureMax;
    TextView textViewTemperatureMin;
    TextView textViewDescription;
    TextView textViewHumidity;
    TextView textViewWindspeed;
    TextView textViewWinddegree;
    TextView textViewCloudiness;
    TextView textViewTitle;
    DecimalFormat df = new DecimalFormat("#.###");


    private String mcity;
    private String mcountry;

    public CityCurrentFragment() {
    }


    public static CityCurrentFragment newInstance(String city,String country) {
        CityCurrentFragment fragment = new CityCurrentFragment();
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

        view= inflater.inflate(R.layout.fragment_city_current, container, false);
        textViewTemperature=view.findViewById(R.id.textViewTemperature);
        textViewTemperatureMax=view.findViewById(R.id.textViewTemperatureMax);
        textViewTemperatureMin=view.findViewById(R.id.textViewTemperatureMin);
        textViewDescription=view.findViewById(R.id.textViewDescription);
        textViewHumidity=view.findViewById(R.id.textViewHumidity);
        textViewWindspeed=view.findViewById(R.id.textViewWindspeed);
        textViewWinddegree=view.findViewById(R.id.textViewWinddegree);
        textViewCloudiness=view.findViewById(R.id.textViewCloudiness);
        textViewTitle=view.findViewById(R.id.textViewTitle);
        ImageView imageViewcurrent=view.findViewById(R.id.imageViewcurrent);
        textViewTitle.setText(mcity+", "+mcountry);

        CallAPI.getcurrent(mcity, new CallAPI.CallHandler() {
            @Override
            public void onSuccess(Object o, String message) {
                WeatherCurrent weatherCurrent= (WeatherCurrent) o;
                if(weatherCurrent!=null)
                {
                    if(getActivity()!=null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                textViewTemperature.setText(String.valueOf(df.format(1.8 * (weatherCurrent.main.temp - 273) + 32)) + " " + getString(R.string.F));
                                textViewTemperatureMax.setText(String.valueOf(df.format((1.8 * (weatherCurrent.main.temp_max - 273)) + 32)) + " " + getString(R.string.F));
                                textViewTemperatureMin.setText(String.valueOf(df.format((1.8 * (weatherCurrent.main.temp_min - 273)) + 32)) + " " + getString(R.string.F));
                                textViewDescription.setText((weatherCurrent.weather.get(0).description));
                                textViewHumidity.setText(String.valueOf(weatherCurrent.main.humidity) + getString(R.string.percent));
                                textViewWindspeed.setText(String.valueOf(weatherCurrent.wind.speed) + " " + getString(R.string.mileshe));
                                textViewWinddegree.setText(String.valueOf(weatherCurrent.wind.deg) + " " + getString(R.string.degrees));
                                textViewCloudiness.setText(String.valueOf(weatherCurrent.clouds.all) + getString(R.string.percent));
                                Log.d("TAG", "run: " + "https://openweathermap.org/img/wn/" + weatherCurrent.weather.get(0).icon + "@2x.png");
                                Picasso.get().load("https://openweathermap.org/img/wn/" + weatherCurrent.weather.get(0).icon + "@2x.png").into((imageViewcurrent));

                            }
                        });
                    }
                }
                else
                {
                    if(getActivity()!=null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                fragmentInterface.gohome();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
        view.findViewById(R.id.buttoncheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goforecast(mcity,mcountry);
            }
        });
        return view;
    }
}