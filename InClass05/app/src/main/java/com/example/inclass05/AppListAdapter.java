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

public class AppListAdapter extends ArrayAdapter<DataServices.App> {


    public AppListAdapter(@NonNull Context context, int resource, @NonNull List<DataServices.App> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_list_layout, parent, false);
            AppViewHolder appViewHolder = new AppViewHolder();
            appViewHolder.textViewappname = convertView.findViewById(R.id.textViewappname);
            appViewHolder.textViewDate = convertView.findViewById(R.id.textViewDate);
            appViewHolder.textViewartist = convertView.findViewById(R.id.textViewartist);
            convertView.setTag(appViewHolder);
        }
        AppViewHolder appViewHolder= (AppViewHolder) convertView.getTag();
        DataServices.App app=getItem(position);
        appViewHolder.textViewappname.setText(app.name);
        appViewHolder.textViewartist.setText(app.artistName);
        appViewHolder.textViewDate.setText(app.releaseDate);

        return convertView;
    }
}
