package com.example.groupb09_hw06;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentHolder extends RecyclerView.ViewHolder {
    TextView textViewcreatedBy1;
    TextView textViewcomment1;
    TextView textViewdate1;
    ImageView imageViewdel1;
    Commentfragmentinterface commentfragmentinterface;
    String muuid;
    String mforumId;
    String mcommentId;
    ArrayList<Comment> commentArrayList;


    public CommentHolder(@NonNull View itemView, Commentfragmentinterface commentfragmentinterface) {
        super(itemView);

        if (itemView!=null)
        {
            textViewcreatedBy1=itemView.findViewById(R.id.textViewcreatedBy1);
            textViewcomment1=itemView.findViewById(R.id.textViewcomment1);
            textViewdate1=itemView.findViewById(R.id.textViewdate1);
            imageViewdel1=itemView.findViewById(R.id.imageViewdel1);


        }
        this.commentfragmentinterface=commentfragmentinterface;

        imageViewdel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentfragmentinterface.deletecomment(commentArrayList.get(getAdapterPosition()).getCommentid());
            }
        });

    }


}
