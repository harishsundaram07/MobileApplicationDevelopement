// Assignment : InClass 08
//File Name : GroupB09_InClass08
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI



package com.example.groupb09_inclass08;


import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Forumviewholder extends RecyclerView.ViewHolder{
    TextView textViewtitle;
    TextView textViewcreatedBy;
    TextView textViewdescription;
    TextView textViewlikedby;
    TextView textViewdate;
    ImageView imageViewdel;

    FunctionInterface functionInterface;

    String forumId;

    public Forumviewholder(@NonNull View itemView,   List<Forum> forumlist, FunctionInterface functionInterface) {
        super(itemView);


        if (itemView!=null)
        {
            textViewtitle=itemView.findViewById(R.id.textViewtitle);
            textViewcreatedBy=itemView.findViewById(R.id.textViewcreatedBy);
            textViewdescription=itemView.findViewById(R.id.textViewdescription);
            textViewdate=itemView.findViewById(R.id.textViewdate);
            imageViewdel=itemView.findViewById(R.id.imageViewdel);

        }
        this.functionInterface=functionInterface;
        imageViewdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(itemView.getContext());
                builder
                        .setMessage(itemView.getContext().getString(R.string.deleteconfrim))
                        .setPositiveButton(itemView.getContext().getString(R.string.confirmation), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                functionInterface.deleteforum(forumId);
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


}
