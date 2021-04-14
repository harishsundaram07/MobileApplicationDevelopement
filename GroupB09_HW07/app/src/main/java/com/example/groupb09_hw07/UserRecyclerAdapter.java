// Assignment : HW 07
//File Name : GroupB09_HW07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI

package com.example.groupb09_hw07;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserRecyclerAdapter  extends RecyclerView.Adapter<Userviewholder>{

    List<User> userlist;
    FunctionInterface functionInterface;
    String muuid;
    View view;

    public UserRecyclerAdapter(List<User> userlist, FunctionInterface functionInterface, String muuid) {
        this.userlist = userlist;
        this.functionInterface = functionInterface;
        this.muuid = muuid;
    }

    @NonNull
    @Override
    public Userviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_holder,parent,false);
        Userviewholder viewHolder=new Userviewholder(view,userlist,functionInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Userviewholder holder, int position) {
        holder.photoscount.setVisibility(View.GONE);
        holder.photoscount.setText(userlist.get(position).getPhotosList().size()+" "+ view.getContext().getString(R.string.photos));
        holder.user = userlist.get(position);
            holder.textViewtitle.setText(userlist.get(position).getName());
            holder.textViewemail1.setText(userlist.get(position).getEmailid());
                      holder.muuid = muuid;
            holder.imageViewidp.setImageResource(R.drawable.profilepic);
              if(userlist.get(position).getProfilepic()!=null && userlist.get(position).getProfilepic().length()>0 ) {
             CallFirebase.setpicture((FragmentActivity) view.getContext(), userlist.get(position).getProfilepic(), new CallFirebase.CallHandler() {
                 @Override
                 public void onSuccess(Object o, String message) {
                     Glide.with(view.getContext())
                             .load((StorageReference)o)
                             .into(holder.imageViewidp);

                 }

                 @Override
                 public void onFailure(String message) {
                 }
             });
         }


    }

    private void setphotolist(ArrayList<Photos> photosArrayList, int position) {
        userlist.get(position).setPhotosList(photosArrayList);
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }
}
