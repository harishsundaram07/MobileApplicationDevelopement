// Assignment : Homework 05
//File Name : GroupB09_HW05
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI


package com.example.groupb09_hw05;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CitylistRecylerAdapter extends RecyclerView.Adapter<CityViewHolder> {

    ArrayList<Data.City> cities;
    FunctionInterface functionInterface;

    public CitylistRecylerAdapter(ArrayList<Data.City> cities, FunctionInterface functionInterface) {
        this.cities = cities;
        this.functionInterface = functionInterface;
    }



    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.citiesviewholder,parent,false);
        CityViewHolder cityViewHolder=new CityViewHolder(view,cities,functionInterface);
        return cityViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        Data.City city=cities.get(position);
        holder.textViewCity.setText(city.getCity()+", "+city.getCountry());

    }




    @Override
    public int getItemCount() {
        return cities.size();
    }
}
