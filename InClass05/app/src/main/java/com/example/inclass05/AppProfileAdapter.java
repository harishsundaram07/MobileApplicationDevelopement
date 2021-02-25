// Assignment : In Class 05
//File Name : InClass05
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.inclass05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AppProfileAdapter extends ArrayAdapter<String> {


    String flag;
    public AppProfileAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
        {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.profile_hiew_holder, parent, false);
                AppViewHolder appViewHolder = new AppViewHolder();
                appViewHolder.textViewappCatg = convertView.findViewById(R.id.textViewappCatg);
                convertView.setTag(appViewHolder);
        }
        AppViewHolder appViewHolder= (AppViewHolder) convertView.getTag();
        appViewHolder.textViewappCatg.setText(getItem(position));
        return convertView;
    }
}
