package com.example.demoapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserAdapter  extends ArrayAdapter<User> {
    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.temp,parent,false);
            ViewHolder viewHolder=new ViewHolder();
            viewHolder.textViewl=convertView.findViewById(R.id.textViewCities);
            viewHolder.textViewm=convertView.findViewById(R.id.textViewm);
            viewHolder.textViews=convertView.findViewById(R.id.textViews);
            convertView.setTag(viewHolder);
        }

        User user=getItem(position);
        ViewHolder viewHolder= (ViewHolder) convertView.getTag();
        viewHolder.textViewl.setText(user.name);
        viewHolder.textViewm.setText(Integer.toString(user.age));
        viewHolder.textViews.setText(user.gender);
        return convertView;
    }

    public static class ViewHolder{
        TextView textViewl;
        TextView textViewm;
        TextView textViews;
    }
}
