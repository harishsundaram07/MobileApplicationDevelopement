// Assignment : Homework 03
//File Name : HW03
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.groupb09hw04;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AppDeatilsViewHolder extends RecyclerView.ViewHolder{

        TextView textViewappCatg;


    AdapterInterface adapterInterface;

    public AppDeatilsViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewappCatg=itemView.findViewById(R.id.textViewappCatg);


    }
}
