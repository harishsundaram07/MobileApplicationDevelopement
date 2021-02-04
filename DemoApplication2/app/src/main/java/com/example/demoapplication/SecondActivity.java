package com.example.demoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        TextView textView=findViewById(R.id.textView3);
        if(getIntent()!=null && getIntent().getExtras()!=null && getIntent().hasExtra(MainActivity.Name_K))
            textView.setText(getIntent().getStringExtra(MainActivity.Name_K));



    }
}