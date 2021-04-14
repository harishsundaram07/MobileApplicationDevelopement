// Assignment : HW 07
//File Name : GroupB09_HW07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI

package com.example.groupb09_hw07;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Profileholder extends RecyclerView.ViewHolder {
     User mloggedinuser;
    ImageView imageViewmainpic;
     ImageView imageViewlike;
     ImageView imageViewdelete;
     ImageView imageViewcomment;
     TextView textViewlikescount;
     TextView textViewviewcomments;
     TextView caption;
     ProfileFunctionInterface profileFunctionInterface;
     TextView textViewprofilenames;
     Photos photos;
     User muser;
     int position;
     boolean flag=true;





    public Profileholder(@NonNull View itemView, ProfileFunctionInterface profileFunctionInterface) {
        super(itemView);
        if(itemView!=null){
            imageViewmainpic = itemView.findViewById(R.id.imageViewmainpic);
            imageViewlike = itemView.findViewById(R.id.imageViewlike);
            imageViewdelete = itemView.findViewById(R.id.imageViewdelete);
            imageViewcomment = itemView.findViewById(R.id.imageViewcomment);
            textViewlikescount = itemView.findViewById(R.id.textViewlikescount);
            caption = itemView.findViewById(R.id.caption);
            textViewprofilenames=itemView.findViewById(R.id.textViewprofilenames);

            this.profileFunctionInterface = profileFunctionInterface;
        }
        imageViewdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(itemView.getContext());
                builder
                        .setMessage(R.string.deleteconfrim)
                        .setPositiveButton(itemView.getContext().getString(R.string.OK), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                profileFunctionInterface.gotoDelete(muser,photos);
                            }
                        }).setNegativeButton(itemView.getContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();

            }
        });
        imageViewlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileFunctionInterface.gotolikedby(flag,muser,mloggedinuser,photos,position);
            }
        });
        imageViewcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileFunctionInterface.gotoComments(muser,mloggedinuser,photos);
            }
        });



    }


}
