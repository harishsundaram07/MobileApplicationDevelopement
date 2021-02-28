// Assignment : Homework 03
//File Name : HW03
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.hw03;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView textViewappname;
    TextView textViewDate;
    TextView textViewartist;
    DataServices.App app;
    AdapterInterface adapterInterface;

    public RecyclerListHolder(@NonNull View itemView, AdapterInterface adapterInterface) {
        super(itemView);
        textViewappname=itemView.findViewById(R.id.textViewappname);
        textViewDate=itemView.findViewById(R.id.textViewDate);
        textViewartist=itemView.findViewById(R.id.textViewartist);

        this.adapterInterface=adapterInterface;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        adapterInterface.OnclickListeneradapter(getAdapterPosition());
    }
}
