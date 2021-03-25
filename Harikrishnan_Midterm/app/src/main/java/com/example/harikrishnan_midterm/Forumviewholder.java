package com.example.harikrishnan_midterm;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Forumviewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView textViewtitle;
    TextView textViewcreatedBy;
    TextView textViewdescription;
    TextView textViewlikedby;
    TextView textViewdate;
    ImageView imageViewdel;
    ImageView imageViewlike;
    FunctionInterface functionInterface;
    String token;
    Long forumId;
    boolean flag=true;

    public Forumviewholder(@NonNull View itemView,   List<DataServices.Forum> forumlist, FunctionInterface functionInterface) {
        super(itemView);


        if (itemView!=null)
        {
            textViewtitle=itemView.findViewById(R.id.textViewtitle);
            textViewcreatedBy=itemView.findViewById(R.id.textViewcreatedBy);
            textViewdescription=itemView.findViewById(R.id.textViewdescription);
            textViewlikedby=itemView.findViewById(R.id.textViewlikedby);
            textViewdate=itemView.findViewById(R.id.textViewdate);
            imageViewdel=itemView.findViewById(R.id.imageViewdel);
            imageViewlike=itemView.findViewById(R.id.imageViewlike);

        }
        this.functionInterface=functionInterface;
        itemView.setOnClickListener(this);
        imageViewlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                functionInterface.likeby( token, String.valueOf(forumId),flag==true?"1":"0");
            }
        });
        imageViewdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(itemView.getContext());
                builder
                        .setMessage(itemView.getContext().getString(R.string.deleteconfrim))
                        .setPositiveButton(itemView.getContext().getString(R.string.confirmation), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                functionInterface.deleteforum(token, String.valueOf(forumId));
                            }
                        }).setNegativeButton(itemView.getContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        functionInterface.goforum(getAdapterPosition());

    }
}
