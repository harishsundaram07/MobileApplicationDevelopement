// Assignment : Homework 04
//File Name : HW04;
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.groupb09hw04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ApplistAdapter extends RecyclerView.Adapter<RecyclerListHolder> {
    List<DataServices.App> appsKeyset;
    AdapterInterface adapterInterface;

    public ApplistAdapter(List<DataServices.App> appsKeyset, AdapterInterface adapterInterface) {
        this.appsKeyset = appsKeyset;
        this.adapterInterface = adapterInterface;
    }


    @NonNull
    @Override
    public RecyclerListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_layout,parent,false);
        RecyclerListHolder viewHolder=new RecyclerListHolder(view,adapterInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerListHolder holder, int position) {
        holder.app=appsKeyset.get(position);
        holder.textViewappname.setText(appsKeyset.get(position).name);
        holder.textViewartist.setText(appsKeyset.get(position).artistName);
        holder.textViewDate.setText(appsKeyset.get(position).releaseDate);
    }

    @Override
    public int getItemCount() {
        return appsKeyset.size();
    }
}
