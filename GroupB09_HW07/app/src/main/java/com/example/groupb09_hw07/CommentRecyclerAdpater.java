// Assignment : HW 07
//File Name : GroupB09_HW07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI



package com.example.groupb09_hw07;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CommentRecylcerAdapter extends RecyclerView.Adapter<CommentHolder> {

    ArrayList<Comment> commentArrayList;
    Commentfragmentinterface commentfragmentinterface;
    User muser;
    View view;
    User mloggedinuser;
    Photos photos;


    public CommentRecylcerAdapter(ArrayList<Comment> commentArrayList,User muser, User mloggedinuser,Photos photos, Commentfragmentinterface commentfragmentinterface) {
        this.commentArrayList = commentArrayList;
        this.commentfragmentinterface = commentfragmentinterface;
        this.muser = muser;
        this.mloggedinuser=mloggedinuser;
        this.photos=photos;

    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.commentholder,parent,false);
        CommentHolder commentHolder=new CommentHolder(view,commentfragmentinterface);
        return commentHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
      holder.textViewcomment1.setText(commentArrayList.get(position).getComment());
      holder.textViewdate1.setText(commentArrayList.get(position).getDate());
      holder.textViewcreatedBy1.setText(commentArrayList.get(position).getName());
      if(commentArrayList.get(position).getUuid().equalsIgnoreCase(mloggedinuser.getUuid()))
          holder.imageViewdel1.setVisibility(View.VISIBLE);
      else
          holder.imageViewdel1.setVisibility(View.GONE);

      holder.commentArrayList=commentArrayList;
      holder.muser=muser;
      holder.mloggedinuser=mloggedinuser;
      holder.photos=photos;
      holder.comment=commentArrayList.get(position);

    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }
}
