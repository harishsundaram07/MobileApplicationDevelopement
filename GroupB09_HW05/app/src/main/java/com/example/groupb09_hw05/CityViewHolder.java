// Assignment : Homework 05
//File Name : GroupB09_HW05
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI

package com.example.groupb09_hw05;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CityViewHolder  extends RecyclerView.ViewHolder implements  View.OnClickListener {

    FunctionInterface functionInterface;
    TextView textViewCity;


    public CityViewHolder(@NonNull View itemView, ArrayList<Data.City> cities, FunctionInterface functionInterface) {
        super(itemView);

        if(itemView!=null)
        {
            textViewCity=itemView.findViewById(R.id.textViewCity);
        }
        this.functionInterface=functionInterface;
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
            functionInterface.oncityclick(getAdapterPosition());
    }
}
