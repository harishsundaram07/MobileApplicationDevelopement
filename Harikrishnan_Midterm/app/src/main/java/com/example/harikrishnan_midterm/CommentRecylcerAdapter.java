package com.example.harikrishnan_midterm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommentRecylcerAdapter extends RecyclerView.Adapter<CommentHolder> {

    ArrayList<DataServices.Comment> commentArrayList;
    Commentfragmentinterface commentfragmentinterface;
    DataServices.Account maccount;
    long forumId;
    View view;
    String token;

    public CommentRecylcerAdapter(ArrayList<DataServices.Comment> commentArrayList, Commentfragmentinterface commentfragmentinterface, DataServices.Account maccount,  String token ,long forumid) {
        this.commentArrayList = commentArrayList;
        this.commentfragmentinterface = commentfragmentinterface;
        this.maccount = maccount;
        this.token=token;
        this.forumId=forumid;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.commentholder,parent,false);
        CommentHolder commentHolder=new CommentHolder(view,commentArrayList,commentfragmentinterface);
        return commentHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        String name=commentArrayList.get(position).getCreatedBy().getName();
        holder.textViewcreatedBy1.setText(name);
        holder.textViewdate1.setText(commentArrayList.get(position).getCreatedAt().toString());
        holder.textViewcomment1.setText(commentArrayList.get(position).getText());
        if(commentArrayList.get(position).createdBy.equals(maccount))
            holder.imageViewdel1.setVisibility(View.VISIBLE);
        else
            holder.imageViewdel1.setVisibility(View.GONE);
        holder.commentId=commentArrayList.get(position).getCommentId();
        holder.forumId=this.forumId;
        holder.token=this.token;


    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }
}
