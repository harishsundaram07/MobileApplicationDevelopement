// Assignment : In Class 06
//File Name : GroupB09_InClass06
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.groupb09_inclass06;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Double> {

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<Double> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.listviewholder,parent,false);
            ListviewHolder listviewHolder=new ListviewHolder();
            listviewHolder.textviewnumbers=convertView.findViewById(R.id.textViewNumber);
            convertView.setTag(listviewHolder);

        }
        ListviewHolder listviewHolder= (ListviewHolder) convertView.getTag();
        if(getItem(position)!=null)
        listviewHolder.textviewnumbers.setText(Double.toString(getItem(position)));



        return convertView;
    }

    private class ListviewHolder {
        TextView textviewnumbers;
    }
}
