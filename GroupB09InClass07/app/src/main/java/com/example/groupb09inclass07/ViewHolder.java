// Assignment : In Class 07
//File Name : GroupB09_InClass07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.groupb09inclass07;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class ViewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{
    TextView textViewName;
    TextView textViewtype;
    TextView textViewphone;
    TextView textViewemail;
    TextView textviewidholder;
    Button buttondelete;
    FunctionInterface functionInterface;


    public ViewHolder(@NonNull View itemView,  List<Contact> contactlist,FunctionInterface functionInterface) {
        super(itemView);

        if(itemView!=null)
        {
            buttondelete=itemView.findViewById(R.id.buttondelete);
            textViewemail=itemView.findViewById(R.id.textViewemail);
            textViewName=itemView.findViewById(R.id.textViewName);
            textViewphone=itemView.findViewById(R.id.textViewphone);
            textViewtype=itemView.findViewById(R.id.textViewtype);
            textviewidholder=itemView.findViewById(R.id.textViewidholder);
        }

        this.functionInterface=functionInterface;
        itemView.setOnClickListener(this);
        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(itemView.getContext());
                builder
                        .setMessage(itemView.getContext().getString(R.string.deleteconfrim)+" "+ contactlist.get(getAdapterPosition()).name+" ?")
                        .setPositiveButton(itemView.getContext().getString(R.string.confirmation), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                functionInterface.delete(getAdapterPosition());

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
        functionInterface.gotodetails(getAdapterPosition());
    }
}
