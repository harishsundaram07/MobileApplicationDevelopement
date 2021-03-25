package com.example.harikrishnan_midterm;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommentHolder extends RecyclerView.ViewHolder {
    TextView textViewcreatedBy1;
    TextView textViewcomment1;
    TextView textViewdate1;
    ImageView imageViewdel1;
    Commentfragmentinterface commentfragmentinterface;
    String token;
    Long forumId;
    long commentId;

    public CommentHolder(@NonNull View itemView, ArrayList<DataServices.Comment> forumlist, Commentfragmentinterface commentfragmentinterface) {
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
                commentfragmentinterface.deletecomment( token,  String.valueOf(forumId),  String.valueOf(commentId));
            }
        });

    }


}
