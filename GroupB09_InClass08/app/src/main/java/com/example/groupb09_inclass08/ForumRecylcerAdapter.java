// Assignment : InClass 08
//File Name : GroupB09_InClass08
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI


package com.example.groupb09_inclass08;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ForumRecyclerAdapter extends RecyclerView.Adapter<Forumviewholder>{
    List<Forum> forumlist;
    FunctionInterface functionInterface;
    String muuid;
    View view;


    public ForumRecyclerAdapter(List<Forum> forumlist, FunctionInterface functionInterface,String uuid) {
        this.forumlist = forumlist;
        this.functionInterface = functionInterface;
        this.muuid=uuid;

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
        holder.textViewcreatedBy.setText(forumlist.get(position).getUsername());
        holder.textViewdate.setText(forumlist.get(position).getDate());
        holder.textViewdescription.setText(forumlist.get(position).getDescription());
        holder.forumId=forumlist.get(position).getForumid();
        if(forumlist.get(position).getUserid().equalsIgnoreCase(muuid))
            holder.imageViewdel.setVisibility(View.VISIBLE);
        else
            holder.imageViewdel.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return forumlist.size();
    }
}

