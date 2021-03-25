package com.example.demoapplication;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class UserRecycleViewHolder extends RecyclerView.ViewHolder {
    TextView textViewl;
    TextView textViewm;
    TextView textViews;
    int position;
    User user;

    public UserRecycleViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewl=itemView.findViewById(R.id.textViewCities);
        textViewm=itemView.findViewById(R.id.textViewm);
        textViews=itemView.findViewById(R.id.textViews);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: "+user.name);
            }
        });

    }
}

