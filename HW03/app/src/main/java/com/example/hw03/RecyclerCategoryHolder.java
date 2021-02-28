package com.example.hw03;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerCategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    DataServices.Account account;
    DataServices.App app;
    TextView textViewappCatg;

    AdapterInterface adapterInterface;

    public RecyclerCategoryHolder(@NonNull View itemView, AdapterInterface adapterInterface) {
        super(itemView);
        if(itemView.findViewById(R.id.textViewappCatg)!=null)
            textViewappCatg=itemView.findViewById(R.id.textViewappCatg);

        this.adapterInterface=adapterInterface;
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        adapterInterface.OnclickListeneradapter(getAdapterPosition());
    }
}
