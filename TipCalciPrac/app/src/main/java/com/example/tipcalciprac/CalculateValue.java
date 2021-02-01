package com.example.tipcalciprac;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;


public class CalculateValue extends AppCompatActivity {

    public CalculateValue(DataModel dataModel, Context baseContext) {

        DecimalFormat df = new DecimalFormat("#.##");
        Double value;
        if (dataModel.getEditText().getText().length() > 0) {
            String input=dataModel.getEditText().getText().toString().charAt(0)=='.'?"0"+dataModel.getEditText().getText().toString():dataModel.getEditText().getText().toString();
            if (dataModel.getRadioGroup().getCheckedRadioButtonId() == R.id.radioButton10) {
                value = Double.parseDouble(input);
                dataModel.getTipview().setText(df.format(value * 0.1));
                dataModel.getTotalview().setText(df.format((value * 0.1) + value));
            } else if (dataModel.getRadioGroup().getCheckedRadioButtonId() == R.id.radioButton15) {
                value = Double.parseDouble(input);
                dataModel.getTipview().setText(df.format(value * 0.15));
                dataModel.getTotalview().setText(df.format((value * 0.15) + value));
            } else if (dataModel.getRadioGroup().getCheckedRadioButtonId() == R.id.radioButton18) {
                value = Double.parseDouble(input);
                dataModel.getTipview().setText(df.format(value * 0.18));
                dataModel.getTotalview().setText(df.format((value * 0.18) + value));
            } else if (dataModel.getRadioGroup().getCheckedRadioButtonId() == R.id.radioButtonCustom) {
                value = Double.parseDouble(input);
                dataModel.getTextViewProgress().setText(String.valueOf(dataModel.getSeekBar().getProgress()) + baseContext.getString((R.string.percent)));
                dataModel.getTipview().setText(df.format((value * dataModel.getSeekBar().getProgress()) / 100));
                dataModel.getTotalview().setText(df.format((value)+(value * dataModel.getSeekBar().getProgress()) / 100));

            }
        }
        else
        {
            dataModel.getTipview().setText("");
            dataModel.getTotalview().setText("");
            Toast.makeText(baseContext,baseContext.getString((R.string.ToastMessage)), Toast.LENGTH_SHORT).show();
            Log.d("TAG", "CalculateValue: ");

        }
    }
}
