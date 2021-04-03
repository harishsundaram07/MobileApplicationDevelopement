package com.example.groupb09_hw06;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CommentRecylcerAdapter extends RecyclerView.Adapter<CommentHolder> {

    ArrayList<Comment> commentArrayList;
    Commentfragmentinterface commentfragmentinterface;
    String muuid;
    String mforumId;
    String mcommentId;
    View view;

    public CommentRecylcerAdapter(ArrayList<Comment> commentArrayList, String uuid,String forumid, Commentfragmentinterface commentfragmentinterface) {
        this.commentArrayList = commentArrayList;
        this.commentfragmentinterface = commentfragmentinterface;
        this.muuid=uuid;
        this.mforumId=forumid;

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
      if(commentArrayList.get(position).getUuid().equalsIgnoreCase(muuid))
          holder.imageViewdel1.setVisibility(View.VISIBLE);
      else
          holder.imageViewdel1.setVisibility(View.GONE);

      holder.mcommentId=commentArrayList.get(position).getCommentid();
      holder.mforumId=mforumId;
      holder.commentArrayList=commentArrayList;

    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }
}
