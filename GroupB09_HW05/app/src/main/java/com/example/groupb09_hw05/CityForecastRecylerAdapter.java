// Assignment : Homework 05
//File Name : GroupB09_HW05
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI


package com.example.groupb09_hw05;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupb09_hw05.datamodel.Forecast;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CityForecastRecylerAdapter extends RecyclerView.Adapter<ForecastViewHolder> {

    public ArrayList<Forecast.List> list;
    DecimalFormat df = new DecimalFormat("#.###");
    View view;

    public CityForecastRecylerAdapter(ArrayList<Forecast.List> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         view= LayoutInflater.from(parent.getContext()).inflate(R.layout.forecastholder,parent,false);
        ForecastViewHolder forecastViewHolder=new ForecastViewHolder(view,list);
        return forecastViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {

        holder.textViewdate.setText(list.get(position).dt_txt);
        holder.textViewtemp.setText(String.valueOf(list.get(position).main.temp)+" "+view.getContext().getString(R.string.F));
        holder.textViewmax.setText(view.getContext().getString(R.string.max)+" "+(list.get(position).main.temp_max)+" "+view.getContext().getString(R.string.F));
        holder.textViewmin.setText(view.getContext().getString(R.string.min)+" "+(list.get(position).main.temp_min)+" "+view.getContext().getString(R.string.F));
        holder.textViewHumidityf.setText(view.getContext().getString(R.string.humidity)+": "+String.valueOf(list.get(position).main.humidity)+view.getContext().getString(R.string.percent));
        holder.textViewdesc.setText(list.get(position).weather.get(0).description.toUpperCase());
        Picasso.get().load("https://openweathermap.org/img/wn/"+list.get(position).weather.get(0).icon+"@2x.png").into((holder.imageViewforecast) );

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
