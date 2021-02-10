package com.example.hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class CreateActivity extends AppCompatActivity {
    TextView textViewDate2;
    Button buttosetdate;
    DatePickerDialog.OnDateSetListener dateSetListener;
    RadioGroup radioGroup;
    Button buttonCancel;
    Button buttonSubmit;
    EditText editTextTextPersonName;
    final public static String createTask="CREATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        setTitle(getString(R.string.createTitle));
        textViewDate2=findViewById(R.id.textViewDate2);
        buttosetdate=findViewById(R.id.buttonsetdtate);
        buttonCancel=findViewById(R.id.buttonCancel);
        buttonSubmit=findViewById(R.id.buttonSubmit);
        radioGroup=findViewById(R.id.radioGroup);
        editTextTextPersonName=findViewById(R.id.editTextTextPersonName);
        radioGroup.check(R.id.radioButtonLow);
        buttosetdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                int year=calendar.get(calendar.YEAR);
                int month=calendar.get(calendar.MONTH);
                int day=calendar.get(calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(CreateActivity.this,android.R.style.Theme_Holo_Dialog_MinWidth,dateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });
        dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date=month+1+"/"+dayOfMonth+"/"+year;
                textViewDate2.setText(date);
            }
        };
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextTextPersonName.getText().length()>0 && textViewDate2.getText().length()>0 && radioGroup.getCheckedRadioButtonId()!=-1) {
                        Intent intent=new Intent(CreateActivity.this,MainActivity.class);
                        String priority=radioGroup.getCheckedRadioButtonId()==R.id.radioButtonHigh?getString(R.string.high):(radioGroup.getCheckedRadioButtonId()==R.id.radioButtonLow?getString(R.string.low):getString(R.string.medium));
                        intent.putExtra(createTask,new Task(editTextTextPersonName.getText().toString(),textViewDate2.getText().toString(),priority) );
                        setResult(RESULT_OK,intent);
                        finish();
                } else if(editTextTextPersonName.getText().length()==0)
                    Toast.makeText(getBaseContext(),getString(R.string.ToastErrorName),Toast.LENGTH_SHORT).show();
                else if(textViewDate2.getText().length()==0)
                    Toast.makeText(getBaseContext(),getString(R.string.ToastErrorDate),Toast.LENGTH_SHORT).show();
            }
        });

    }
}