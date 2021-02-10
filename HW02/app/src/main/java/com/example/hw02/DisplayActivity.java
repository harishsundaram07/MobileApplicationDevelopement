package com.example.hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayActivity extends AppCompatActivity {
    TextView textView5;
    TextView textView6;
    TextView textView7;
    Button buttonCancel;
    Button buttonDelete;
    final public static String Display_Task="Display";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        setTitle(getString(R.string.DisplayTitle));
        textView5=findViewById(R.id.textView5);
        textView6=findViewById(R.id.textView6);
        textView7=findViewById(R.id.textView7);
        buttonCancel=findViewById(R.id.buttonCancelDisplay);
        buttonDelete=findViewById(R.id.buttonDelete);

        if(getIntent()!=null && getIntent().getExtras()!=null && getIntent().hasExtra(Display_Task))
        {
            Task task=getIntent().getParcelableExtra(Display_Task);
            textView5.setText(task.name);
            textView6.setText(task.date);
            textView7.setText(task.priority);
        }
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),getString(R.string.ToastDelete),Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK,getIntent());
                finish();
            }
        });
    }
}