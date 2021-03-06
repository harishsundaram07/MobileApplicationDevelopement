// Assignment : Homework 03
//File Name : HW03
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.groupb09hw04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecylcerViewCategoriesAdapter extends RecyclerView.Adapter<RecyclerCategoryHolder> {

    List<String> appsKeyset;
    DataServices.Account account1;
    AdapterInterface adapterInterface;

    public RecylcerViewCategoriesAdapter(List<String> appsKeyset, DataServices.Account account, AdapterInterface adapterInterface) {
        this.appsKeyset = appsKeyset;
        this.account1=account;
        this.adapterInterface=adapterInterface;

    }

    @NonNull
    @Override
    public RecyclerCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_row_holder,parent,false);
        RecyclerCategoryHolder viewHolder=new RecyclerCategoryHolder(view,adapterInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerCategoryHolder holder, int position) {
        holder.textViewappCatg.setText(appsKeyset.get(position));
        holder.account=account1;

    }

    @Override
    public int getItemCount() {
        return appsKeyset.size();
    }
}
