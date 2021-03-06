// Assignment : Homework 03
//File Name : HW03
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.groupb09hw04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecylcerDetailsAdapter  extends RecyclerView.Adapter<AppDeatilsViewHolder> {

    List<String> genres;
    DataServices.Account account1;
    AdapterInterface adapterInterface;

    public RecylcerDetailsAdapter(ArrayList<String> genres) {
        this.genres = genres;
    }


    @NonNull
    @Override
    public AppDeatilsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_row_holder,parent,false);
        AppDeatilsViewHolder viewHolder=new AppDeatilsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppDeatilsViewHolder holder, int position) {
        holder.textViewappCatg.setText(genres.get(position));

    }

    @Override
    public int getItemCount() {
        return genres.size();
    }
}