package com.example.groupb09inclass07;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<ViewHolder> {

    List<Contact> contactlist;
    Contact contact;
    FunctionInterface functionInterface;


    public RecyclerviewAdapter(List<Contact> contactlist,FunctionInterface functionInterface) {
        this.contactlist = contactlist;
        this.functionInterface = functionInterface;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_holder,parent,false);
        ViewHolder viewHolder=new ViewHolder(view,contactlist,functionInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.textViewemail.setText(viewHolder.itemView.getContext().getString(R.string.email_id)+" "+contactlist.get(position).email);
        viewHolder.textViewName.setText(viewHolder.itemView.getContext().getString(R.string.Name)+" "+contactlist.get(position).name);
        viewHolder.textViewphone.setText(viewHolder.itemView.getContext().getString(R.string.phone_no)+" "+contactlist.get(position).phone);
        viewHolder.textViewtype.setText(viewHolder.itemView.getContext().getString(R.string.Type)+" "+contactlist.get(position).type);
        viewHolder.textviewidholder.setText(viewHolder.itemView.getContext().getString(R.string.id).toUpperCase()+" "+contactlist.get(position).id);

    }

    @Override
    public int getItemCount() {
        return contactlist.size();
    }
}
