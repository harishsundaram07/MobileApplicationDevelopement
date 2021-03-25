package com.example.groupb09_hw05;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupb09_hw05.datamodel.Forecast;

import java.util.ArrayList;

public class ForecastViewHolder extends RecyclerView.ViewHolder {

    TextView textViewmax;
    TextView textViewtemp;
    TextView textViewdate;
    TextView textViewHumidityf;
    TextView textViewmin;
    TextView textViewdesc;
    ImageView imageViewforecast;
    public ForecastViewHolder(@NonNull View itemView, ArrayList<Forecast.List> list) {
        super(itemView);

        if(itemView!=null){
             textViewmax=itemView.findViewById(R.id.textViewmax);
             textViewtemp=itemView.findViewById(R.id.textViewtemp);
             textViewdate=itemView.findViewById(R.id.textViewdate);
             textViewHumidityf=itemView.findViewById(R.id.textViewHumidityf);
             textViewmin=itemView.findViewById(R.id.textViewmin);
             textViewdesc=itemView.findViewById(R.id.textViewdesc);
            imageViewforecast=itemView.findViewById(R.id.imageViewforecast);
        }
    }
}
