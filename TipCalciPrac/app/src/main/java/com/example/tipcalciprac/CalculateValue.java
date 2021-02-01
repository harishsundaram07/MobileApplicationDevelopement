package com.example.tipcalciprac;

import android.content.Context;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class CalculateValue {

    public CalculateValue(RadioGroup radioGroup, EditText editText, int checkRadioId, TextView tipview, TextView totalview, SeekBar seekBar, Context baseContext, TextView textViewProgress) {
        DecimalFormat df = new DecimalFormat("#.##");
        Double value;
        if (editText.getText().length() > 0) {
            String input=editText.getText().toString().charAt(0)=='.'?"0"+editText.getText().toString():editText.getText().toString();
            if (checkRadioId == R.id.radioButton10) {
                value = Double.parseDouble(input);
                tipview.setText(df.format(value * 0.1));
                totalview.setText(df.format((value * 0.1) + value));
            } else if (checkRadioId == R.id.radioButton15) {
                value = Double.parseDouble(input);
                tipview.setText(df.format(value * 0.15));
                totalview.setText(df.format((value * 0.15) + value));
            } else if (checkRadioId == R.id.radioButton18) {
                value = Double.parseDouble(input);
                tipview.setText(df.format(value * 0.18));
                totalview.setText(df.format((value * 0.18) + value));
            } else if (checkRadioId == R.id.radioButtonCustom) {
                value = Double.parseDouble(input);
                textViewProgress.setText(String.valueOf(seekBar.getProgress()) + "%");
                tipview.setText(df.format((value * seekBar.getProgress()) / 100));
                totalview.setText(df.format((value)+(value * seekBar.getProgress()) / 100));
            }
        }
        else
        {
            tipview.setText("");
            totalview.setText("");
            Toast.makeText(baseContext, "Enter Bill Total.", Toast.LENGTH_SHORT).show();
        }


    }
}
