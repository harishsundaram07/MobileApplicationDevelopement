// Assignment : InClass 08
//File Name : GroupB09_InClass08
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI


// Assignment : HW 06
//File Name : GroupB09_HW06
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI

package com.example.groupb09_hw06;


import android.content.DialogInterface;
import android.util.Log;
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
    String forumId;
    String muuid;
    Forum forum;
    boolean like=true;

    public Forumviewholder(@NonNull View itemView, List<Forum> forumlist, FunctionInterface functionInterface) {
        super(itemView);


        if (itemView != null) {
            textViewtitle = itemView.findViewById(R.id.textViewtitle);
            textViewcreatedBy = itemView.findViewById(R.id.textViewcreatedBy);
            textViewdescription = itemView.findViewById(R.id.textViewdescription);
            textViewdate = itemView.findViewById(R.id.textViewdate);
            imageViewdel = itemView.findViewById(R.id.imageViewdel);
            textViewlikedby = itemView.findViewById(R.id.textViewlikedby);
            imageViewlike = itemView.findViewById(R.id.imageViewlike);
            imageViewlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    functionInterface.likedby(forum, muuid, forum.likedby.contains(muuid) ? false : true);
                    Log.d("TAG", "onClick: ");
                }
            });
            this.functionInterface = functionInterface;
            imageViewdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
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
itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
            functionInterface.gotoDetails(muuid,forum);
    }
}
