package com.example.harikrishnan_midterm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ForumRecyclerAdapter extends RecyclerView.Adapter<Forumviewholder>{
    List<DataServices.Forum> forumlist;
    DataServices.Forum forum;
    FunctionInterface functionInterface;
    View view;
    DataServices.Account account;
    String mtoken;

    public ForumRecyclerAdapter(List<DataServices.Forum> forumlist,DataServices.Account maccount, String token, FunctionInterface functionInterface) {
        this.forumlist = forumlist;
        this.functionInterface = functionInterface;
        this.account=maccount;
        this.mtoken=token;
    }




    @NonNull
    @Override
    public Forumviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

         view= LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_list_holder,parent,false);
        Forumviewholder viewHolder=new Forumviewholder(view,forumlist,functionInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Forumviewholder holder, int position) {
        holder.textViewtitle.setText(forumlist.get(position).getTitle());
        holder.textViewcreatedBy.setText(forumlist.get(position).getCreatedBy().getName());
        if((forumlist.get(position).getDescription()).length()>200)
        holder.textViewdescription.setText((forumlist.get(position).getDescription()).substring(0,200));
        else
            holder.textViewdescription.setText((forumlist.get(position).getDescription()));
        holder.textViewlikedby.setText(Integer.toString(forumlist.get(position).getLikedBy().size())+" "+ view.getContext().getString(R.string.likes));
        holder.textViewdate.setText(forumlist.get(position).getCreatedAt().toString());
        if(forumlist.get(position).getLikedBy().contains(account))
        {
            holder.imageViewlike.setImageResource(R.drawable.like_favorite);
            holder.flag=false;
        }
            else
        {
            holder.imageViewlike.setImageResource(R.drawable.like_not_favorite);
            holder.flag=true;
        }
        if(forumlist.get(position).getCreatedBy().equals(account))
          holder.imageViewdel.setVisibility(View.VISIBLE);
        else
            holder.imageViewdel.setVisibility(View.GONE);
        holder.forumId=forumlist.get(position).getForumId();
            holder.token=mtoken;

    }

    @Override
    public int getItemCount() {
        return forumlist.size();
    }
}
