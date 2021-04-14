// Assignment : HW 07
//File Name : GroupB09_HW07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI

package com.example.groupb09_hw07;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfilerecylcerAdapter extends RecyclerView.Adapter<Profileholder> {


    ArrayList<Photos> photos;
    User muser;
    ProfileFunctionInterface profileFunctionInterface;
    View view;
    User mloggedinuser;

    public ProfilerecylcerAdapter(ArrayList<Photos> photos, User muser, User mloggedinuser, ProfileFunctionInterface profileFunctionInterface) {
        this.muser = muser;
        this.profileFunctionInterface = profileFunctionInterface;
        this.photos=photos;
        this.mloggedinuser=mloggedinuser;
    }



    @NonNull
    @Override
    public Profileholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.photoholder,parent,false);
        Profileholder viewHolder=new Profileholder(view,profileFunctionInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Profileholder holder, int position) {
        if( photos!=null && photos.size()>0 ) {
            CallFirebase.setpicture((FragmentActivity) view.getContext(), photos.get(position).getPhotoid(), new CallFirebase.CallHandler() {
                @Override
                public void onSuccess(Object o, String message) {
                    Glide.with(view.getContext())
                            .load((StorageReference) o)
                            .into(holder.imageViewmainpic);
                }

                @Override
                public void onFailure(String message) {
                }
            });
            holder.textViewprofilenames.setText(photos.get(position).getName());
            holder.caption.setText(photos.get(position).getCaption());
            holder.textViewlikescount.setText(photos.get(position).getLikedby().size() + " " + view.getContext().getString(R.string.likes));
            if (mloggedinuser.getUuid().equalsIgnoreCase(muser.getUuid()))
                holder.imageViewdelete.setVisibility(View.VISIBLE);
            else
                holder.imageViewdelete.setVisibility(View.GONE);

            if (photos.get(position).getLikedby().contains(mloggedinuser.getUuid()))
            {
                holder.imageViewlike.setImageResource(R.drawable.like_favorite);
                holder.flag=false;
            }
            else
            {
                holder.imageViewlike.setImageResource(R.drawable.like_not_favorite);
                holder.flag=true;
            }
            holder.muser=muser;
            holder.mloggedinuser=mloggedinuser;
            holder.photos=photos.get(position);
            holder.position=position;



        }
        }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}
