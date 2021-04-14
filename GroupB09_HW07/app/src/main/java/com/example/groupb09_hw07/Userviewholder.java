// Assignment : HW 07
//File Name : GroupB09_HW07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI



package com.example.groupb09_hw07;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Userviewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView textViewtitle;
    TextView photoscount;
    TextView textViewemail1;
    FunctionInterface functionInterface;
    ImageView imageViewidp;
    String muuid;
    User user;
    boolean like=true;

    public Userviewholder(@NonNull View itemView, List<User> userlist, FunctionInterface functionInterface) {
        super(itemView);


        if (itemView != null) {
            textViewtitle = itemView.findViewById(R.id.textViewname);
            photoscount = itemView.findViewById(R.id.photoscount);
            textViewemail1 = itemView.findViewById(R.id.textViewemail1);
            imageViewidp=itemView.findViewById(R.id.imageViewidp);
            this.functionInterface = functionInterface;
            itemView.setOnClickListener(this);

        }
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
            functionInterface.gotoDetails(user);
    }
}
