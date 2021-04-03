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

public class UserAdapter  extends ArrayAdapter<Person> {
    public UserAdapter(@NonNull Context context, int resource, @NonNull List<Person> objects) {
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

            convertView.setTag(viewHolder);
        }

        Person user=getItem(position);
        ViewHolder viewHolder= (ViewHolder) convertView.getTag();
        viewHolder.textViewl.setText(user.getName());
        return convertView;
    }

    public static class ViewHolder{
        TextView textViewl;
        TextView textViewm;
        TextView textViews;
    }
}
