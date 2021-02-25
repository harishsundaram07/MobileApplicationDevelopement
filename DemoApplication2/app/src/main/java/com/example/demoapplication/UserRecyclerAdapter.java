package com.example.demoapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecycleViewHolder> {

    List<User> users;

    public UserRecyclerAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.temp,parent,false);
        UserRecycleViewHolder userRecycleViewHolder=new UserRecycleViewHolder(view);
        return userRecycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecycleViewHolder holder, int position) {
        User user=users.get(position);
        holder.textViewl.setText(user.name);
        holder.textViewm.setText(Integer.toString(user.age));
        holder.textViews.setText(user.gender);
        holder.position=position;
        holder.user=user;


    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
